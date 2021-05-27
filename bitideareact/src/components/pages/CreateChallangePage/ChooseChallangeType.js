import React, { Component } from 'react'
import createChallangeStyle from '../../../styles/css/createChallange.module.css';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import axios from 'axios';
import errorHandler from '../../tools/errorHandler';

export default class ChooseChallangeType extends Component {
    constructor(props){
        super(props);
        this.state = {
            modal:false,
            groups:[]
        }
    }

    chooseGroup = () => {
        axios
            .post(
                "/group/getUserGroups",
                {},
                {
                    headers:{
                        Authorization:`Bearer ${localStorage.getItem('token')}`
                    }
                }
            )
            .then(response => {
                this.setState({
                    groups:response.data
                });
                this.toggle();
            })
            .catch(error => errorHandler.handleWithHistoryObject(error))
    }

    toggle = () => this.setState({modal:!this.state.modal})

    render() {
        return (
            <div className={createChallangeStyle.chooseContainer}>
                <div
                    onClick={() => this.props.changeForm(null)}
                    className={[createChallangeStyle.chooseBox, createChallangeStyle.publicBox].join(' ')}>

                </div>
                <div
                    onClick={this.chooseGroup}
                    className={[createChallangeStyle.chooseBox, createChallangeStyle.privateBox].join(' ')}>

                </div>
                <Modal isOpen={this.state.modal} toggle={this.toggle}>
                    <ModalHeader toggle={this.toggle}>Paylaşım yapmak istediğiniz grubu seçin:</ModalHeader>
                    <ModalBody className={createChallangeStyle.groupsCardContainer}>
                        <ul className={createChallangeStyle.groupsCard} >
                            {
                                this.state.groups.map((item,index) => (
                                    <li 
                                        onClick={() => this.props.changeForm(item)} 
                                        key={index}   
                                    >
                                        {item.name}
                                    </li>
                                ))
                            }
                        </ul>
                    </ModalBody>
                </Modal>
            </div>
        )
    }
}
