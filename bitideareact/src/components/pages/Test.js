import React, { Component } from 'react'
import challangeStyle from '../../styles/css/challange.module.css'
import AceEditor from 'react-ace';
import "ace-builds/src-noconflict/mode-java";
import "ace-builds/src-noconflict/theme-eclipse";

export default class Test extends Component {

    constructor(props){
        super(props);
        this.state = {
            code:"public class Main{\n\tpublic static void main(String[] args){\n\t\tSystem.out.println(\"hello world\");\n\t}\n}"
        }
    }

    onChange(val){
        this.setState({
            code:val
        })
    } 

    render() {
        return (
            <div className={challangeStyle.appContainer}>
                <AceEditor
                    mode="java"
                    theme="eclipse"
                    onChange={this.onChange.bind(this)}
                    name="challangeEditor"
                    editorProps={{$blockScrolling:true}}
                    className={challangeStyle.editor}
                    value={this.state.code}
                />
            </div>
        )
    }
}
