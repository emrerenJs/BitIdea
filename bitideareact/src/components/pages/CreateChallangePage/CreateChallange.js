import React, { Component } from 'react'
import createChallangeStyle from '../../../styles/css/createChallange.module.css';

import ChallangeForm from './ChallangeForm';
import ChooseChallangeType from './ChooseChallangeType';

export default class CreateChallange extends Component {
    constructor(props){
        super(props);
        this.state = {
            challangeForm:false,
            group:null
        }
    }

    changeForm = (group) => {
        this.setState({
            challangeForm:true,
            group
        });
    }

    render() {
        return (
            <div>
                <div className={createChallangeStyle.spaceFromTop}>

                </div>
                {
                    this.state.challangeForm
                    ?
                    (
                        <ChallangeForm
                            history={this.props.history}
                            group={this.state.group}
                        />
                    ):
                    (
                        <ChooseChallangeType
                            changeForm={this.changeForm}
                        />
                    )
                }
                
            </div>
        )
    }
}
