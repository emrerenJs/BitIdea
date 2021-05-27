import React, { Component } from 'react'
import createChallangeStyle from '../../../styles/css/createChallange.module.css';

import { CKEditor } from '@ckeditor/ckeditor5-react';
import CustomEditor from 'ckeditor5-custom-build/build/ckeditor';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPlus } from '@fortawesome/free-solid-svg-icons';
import ModalAddNewParameter from './ModalAddNewParameter';
import ModalChangeOutput from './ModalChangeOutput';
import ModalChangeParameter from './ModalChangeParameter';
import axios from 'axios';
import errorHandler from '../../tools/errorHandler';

const editorConfiguration = {
    toolbar: ['heading', '|', 'bold', 'italic', 'numberedList', 'bulletedList', 'insertTable', 'undo', 'redo']
}

export default class ChallangeForm extends Component {

    constructor(props) {
        super(props);
        this.state = {
            challangeHeader:"Harika Uygulama",
            postContent: "<p>Uygulama detayları..</p>",
            testScenarios:
                [
                    {
                        inputs: [
                        ],
                        output: {
                            dimension: 0,
                            type: "Integer",
                            value: 0
                        }
                    }
                ],
            parameterCount: 0
        }
    }

    saveChallange(){
        axios
            .post(
                "/challange/createChallange",
                {
                    challangeHeader:this.state.challangeHeader,
                    challangeBody:this.state.postContent,
                    testScenariosString:JSON.stringify(this.state.testScenarios),
                    groupName:this.props.group ? this.props.group.name : ""
                },
                {
                    headers:{
                        Authorization:`Bearer ${localStorage.getItem('token')}`
                    }
                }                
            )
            .then(data => console.log(data))
            .catch(error => errorHandler.handleWithHistoryObject(this.props.history,error))
    }

    ckeditorOnChange = (event, editor) => {
        const data = editor.getData();
        this.setState({
            postContent: data
        })
    }

    getValues = (parameter) => {
        let value = "";
        for (let i = 0; i < parameter.dimension; i++) {
            value += "["
        }
        value += ` ${parameter.type} `
        for (let i = 0; i < parameter.dimension; i++) {
            value += "]"
        }
        return value;
    }

    denyChallange() {
        this.props.history.push("/home")
    }

    addNewParameter(scenarioIndex, parameter) {
        const testScenarios = this.state.testScenarios;
        for (let i = 0; i < testScenarios.length; i++) {
            testScenarios[i].inputs.push(JSON.parse(JSON.stringify(parameter)));
        }
        this.setState({
            testScenarios,
            parameterCount: this.state.parameterCount + 1
        });
    }

    denyParameter(parameterIndex) {
        const testScenarios = this.state.testScenarios;
        for (let i = 0; i < testScenarios.length; i++) {
            testScenarios[i].inputs.splice(parameterIndex, 1);
        }
        this.setState({
            testScenarios,
            parameterCount: this.state.parameterCount - 1
        });
    }

    addNewScenario() {
        if (this.state.parameterCount === 0) {
            alert("Lütfen önce 1. test senaryosunu düzenleyin!");
        } else {
            const testScenarios = this.state.testScenarios;
            testScenarios.push(JSON.parse(JSON.stringify(testScenarios[0])));
            this.setState({
                testScenarios
            });
        }
    }

    denyScenario(index) {
        const testScenarios = this.state.testScenarios;
        testScenarios.splice(index, 1);
        this.setState({
            testScenarios
        });
    }

    changeOutput(scenarioIndex, output) {
        const testScenarios = this.state.testScenarios;
        testScenarios[scenarioIndex].output = output;
        this.setState({
            testScenarios
        });
    }

    changeParameter(scenarioIndex,parameterIndex,parameter){
        const testScenarios = this.state.testScenarios;
        testScenarios[scenarioIndex].inputs[parameterIndex] = parameter;
        this.setState({
            testScenarios
        });
    }
    
    challangeHeaderChangeHandler(e){
        this.setState({
            challangeHeader:e.target.value
        })
    }


    render() {
        return (
            <div className={createChallangeStyle.formContainer}>
                <input value={this.state.challangeHeader} onChange={this.challangeHeaderChangeHandler.bind(this)} placeholder="Harika Uygulama" type="text" className={createChallangeStyle.challangeHeader} />
                <CKEditor
                    editor={CustomEditor}
                    data={this.state.postContent}
                    config={editorConfiguration}
                    onChange={this.ckeditorOnChange}
                />
                <h1 style={{ marginTop: "25px" }}>Test Senaryoları</h1>
                <ul className={createChallangeStyle.testScenarios}>
                    {
                        this.state.testScenarios.map((item, index) => (
                            <li>
                                {
                                    index !== 0
                                    &&
                                    (
                                        <div className={createChallangeStyle.denyScenario} onClick={this.denyScenario.bind(this, index)}>
                                            x
                                        </div>
                                    )
                                }
                                <ul className={createChallangeStyle.parameters}>
                                    {
                                        this.state.testScenarios[index].inputs.map((parameter, pIndex) => (
                                            <li>
                                                <div className={createChallangeStyle.parameterInteractions}>
                                                    <ModalChangeParameter
                                                        changeParameter={this.changeParameter.bind(this)}
                                                        scenarioIndex={index}
                                                        parameterIndex={pIndex}
                                                        parameter={this.state.testScenarios[index].inputs[pIndex]}
                                                    />
                                                    {
                                                        index === 0
                                                        &&
                                                        (
                                                            <div onClick={this.denyParameter.bind(this, pIndex)} className={createChallangeStyle.parameterDenyButton}>
                                                                x
                                                            </div>
                                                        )
                                                    }
                                                </div>
                                                <div className={createChallangeStyle.parameter}>
                                                    {this.getValues(parameter)}
                                                </div>
                                            </li>
                                        ))
                                    }
                                    <ModalChangeOutput
                                        scenarioIndex={index}
                                        changeOutput={this.changeOutput.bind(this)}
                                    />
                                    {
                                        index === 0
                                        &&
                                        (
                                            <ModalAddNewParameter
                                                scenarioIndex={index}
                                                addNewParameter={this.addNewParameter.bind(this)}
                                            />
                                        )
                                    }
                                </ul>
                            </li>
                        ))
                    }
                    <li>
                        <div className={createChallangeStyle.addButton} onClick={this.addNewScenario.bind(this)}>
                            <div className={createChallangeStyle.addButtonInside}>
                                <FontAwesomeIcon icon={faPlus} className={createChallangeStyle.addIcon} />
                            </div>
                        </div>
                    </li>
                </ul>
                <div className={createChallangeStyle.operations}>
                    <div
                        onClick={this.saveChallange.bind(this)}
                        className={createChallangeStyle.saveButton}>
                        Kaydet
                    </div>
                    <div
                        onClick={this.denyChallange.bind(this)}
                        className={createChallangeStyle.denyButton}>
                        İptal
                    </div>
                </div>
            </div>
        )
    }
}
