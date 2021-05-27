import React, { Component } from 'react'
import challangeStyle from '../../../styles/css/challange.module.css'
import AceEditor from 'react-ace';
import "ace-builds/src-noconflict/mode-java";
import "ace-builds/src-noconflict/theme-eclipse";
import axios from 'axios';
import errorHandler from '../../tools/errorHandler';
import { Redirect } from 'react-router';
import {Input} from 'reactstrap';
import types from '../../tools/types';

export default class Challange extends Component {

    constructor(props){
        super(props);
        this.state = {
            redirect:{
                option: false,
                page: null,
                message: null
            },
            challange:{},
            programminLanguage:"java",
            code:"//please don't create public class\n//please don't remove this class & function"
        }
    }

    getParameters(){
        let parameterString = "";
        if(this.state.programminLanguage === "java"){
            const parameterTypes = this.state.challange.parameterTypes;
            for(let i = 0; i < parameterTypes.length; i++ ){
                parameterString += types[this.state.programminLanguage][parameterTypes[i].type]
                for(let j = 0; j < parameterTypes[i].dimension; j++){
                    parameterString += "[]";
                }
                parameterString += ` param${i}`;
                if(i !== parameterTypes.length - 1){
                    parameterString +=",";
                }
            }
        }
        return parameterString;
    }

    getReturnString(){
        let returnString = "";
        returnString += types[this.state.programminLanguage][this.state.challange.outputType.type];
        if(this.state.programminLanguage === "java"){
            for(let i = 0; i < this.state.challange.outputType.dimension; i++){
                returnString += "[]";
            }
        }
        return returnString;
    }

    setCodeString(language){
        if(language === "java"){
            let codeString = `\nclass p_${this.state.challange.header.replace(/\s+/g,'')}_${JSON.parse(localStorage.getItem('profile')).username}{\n`;
            codeString += `\tpublic ${this.getReturnString()} ${this.state.challange.header.replace(/\s+/g,'')}f(${this.getParameters()}){\n`;
            codeString += "\t\t//your code goes here!\n";
            codeString += "\t}\n";
            codeString += "}\n";
            this.setState({
                code:this.state.code + codeString,
                programminLanguage:language
            });
        }
    }
    
    submitChallange(){
        const programmingLanguage = this.state.programminLanguage.charAt(0).toUpperCase() + this.state.programminLanguage.slice(1);
        axios
            .post(
                "/challange/submitAnswer",
                {
                    challangeID:this.state.challange.id,
                    challangeAnswer:this.state.code,
                    programmingLanguage
                },
                {
                    headers:{
                        Authorization:`Bearer ${localStorage.getItem('token')}`
                    }
                }
            )
            .then(data => {
                console.log("ok");
            })
            .catch(err => {
                this.setState({
                    redirect:errorHandler.handleWithRedirectObject(err)
                })
            })
    }

    componentDidMount() {
        axios
            .post('/challange/getChallangeById', { challangeId: this.props.match.params.challangeid },{headers:{Authorization:`Bearer ${localStorage.getItem('token')}`}})
            .then(response => response.data)
            .then(data => {
                this.setState({
                    challange:data
                });
                this.setCodeString("java");
            })
            .catch(error => {
                this.setState({redirect:errorHandler.handleWithRedirectObject(error)});
            });
    }

    onChange(val){
        this.setState({
            code:val
        })
    } 

    render() {
        if (this.state.redirect.option) {
            return(
                <Redirect to={this.state.redirect.page}/>
            )
        } else {
            return (
                <div className={challangeStyle.appContainer}>
                    <div className={challangeStyle.spaceFromTop}>

                    </div>
                    <div className={challangeStyle.challangeContainer}>
                        <div className={challangeStyle.challangeHeader}>
                            <div className={challangeStyle.challangeName}>
                                {this.state.challange.header}
                            </div>
                            <div className={challangeStyle.chooseLanguage}>
                                <Input type="select">
                                    <option>Java</option>
                                    <option>C</option>
                                </Input>
                            </div>
                        </div>
                        <div className={challangeStyle.challangeBody}>
                            <div className={challangeStyle.challangeDescription} dangerouslySetInnerHTML={{ __html: this.state.challange.body }}>
                            </div>
                            <div className={challangeStyle.codeEditor}>
                                <AceEditor
                                    mode={this.state.programminLanguage}
                                    theme="eclipse"
                                    onChange={this.onChange.bind(this)}
                                    name="challangeEditor"
                                    editorProps={{$blockScrolling:true}}
                                    className={challangeStyle.editor}
                                    value={this.state.code}
                                />
                            </div>
                        </div>
                        <div className={challangeStyle.challangeFooter}>
                            <div className={challangeStyle.submitChallange} onClick={this.submitChallange.bind(this)}>
                                Gönder
                            </div>
                        </div>
                    </div>
                </div>
            )
        }
    }
}
