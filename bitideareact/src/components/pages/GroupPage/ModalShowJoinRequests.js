import React, { useState } from 'react';
import { Modal, ModalHeader, ModalBody } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBell, faCheck, faTimes } from '@fortawesome/free-solid-svg-icons';
import axios from 'axios';
import groupStyle from '../../../styles/css/group.module.css';
import errorHandler from '../../tools/errorHandler';
import {useHistory} from 'react-router-dom';

const ModalShowJoinRequests = (props) => {
    const {
        className
    } = props;

    const [modal, setModal] = useState(false);
    const history = useHistory();

    const toggle = () => setModal(!modal);

    const acceptUser = (user) => {
        alert("ok");
        axios
            .post("/group/acceptUser",{groupName:props.groupName,username:user.username},{headers:{Authorization:`Bearer ${localStorage.getItem('token')}`}})
            .then(data => window.location.reload())
            .catch(error => {
                errorHandler.handleWithHistoryObject(history,error);
            });
        toggle();
    }
    
    const denyUser = (user) => {
        axios
            .post("/group/denyUser",{groupName:props.groupName,username:user.username},{headers:{Authorization:`Bearer ${localStorage.getItem('token')}`}})
            .then(data => window.location.reload())
            .catch(error => {
                errorHandler.handleWithHistoryObject(history,error);
            });
        toggle();
    }

    return (
        <div>
            <div className={groupStyle.jrNotifications}>
                <FontAwesomeIcon onClick={toggle} icon={faBell} style={{ fontSize: 30, color: '#959728', marginRight: '15px', cursor: 'pointer' }} />
                {
                    props.groupJoinRequests.length > 0
                    &&
                    (
                        <div className={groupStyle.jrNotifCount}>
                            {props.groupJoinRequests.length}
                        </div>
                    )
                }
            </div>
            <Modal isOpen={modal} toggle={toggle} className={className}>
                <ModalHeader toggle={toggle}>Katılım İstekleri</ModalHeader>
                <ModalBody>
                    {
                        props.groupJoinRequests.length > 0
                        ?
                        (
                            <p>{props.groupJoinRequests.length} adet katılım isteğiniz var</p>
                        )
                        :
                        (
                            <p>Hiç katılım isteğiniz yok!</p>
                        )
                    } 
                    <ul className={groupStyle.joinRequests}>
                        <div className={groupStyle.jrhr}></div>
                        {
                            props.groupJoinRequests.map((item, index) => (
                                <li className={groupStyle.joinRequest}>
                                    <div className={groupStyle.jrUserPhoto}>
                                        <img alt="" src={item.photo ? item.photo : "/nophoto.jpg"} />
                                    </div>
                                    <div className={groupStyle.jrInfo}>
                                        <b>{item.username}</b>
                                        <p>{item.firstname} {item.lastname}</p>
                                    </div>
                                    <div className={groupStyle.jrButtons}>
                                        <div onClick={() => acceptUser(item)} className={groupStyle.jrAcceptButton}>
                                            <FontAwesomeIcon icon={faCheck}/>
                                        </div>
                                        <div onClick={() => denyUser(item)} className={groupStyle.jrDenyButton}>
                                            <FontAwesomeIcon icon={faTimes}/>
                                        </div>
                                    </div>         
                                </li>
                            ))
                        }
                    </ul>
                </ModalBody>
            </Modal>
        </div>
    );
}

export default ModalShowJoinRequests;