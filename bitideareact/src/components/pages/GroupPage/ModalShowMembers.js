import React, { useState } from 'react';
import { Modal, ModalHeader, ModalBody } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTimes } from '@fortawesome/free-solid-svg-icons';
import axios from 'axios';
import groupStyle from '../../../styles/css/group.module.css';
import errorHandler from '../../tools/errorHandler';
import {useHistory} from 'react-router-dom';

const ModalShowMembers = (props) => {
    const {
        className
    } = props;

    const [modal, setModal] = useState(false);
    const history = useHistory();

    const toggle = () => setModal(!modal);

    const kickUser = (user) => {
        axios
            .post("/group/kickUser", { groupName: props.groupName, username: user.username }, { headers: { Authorization: `Bearer ${localStorage.getItem('token')}` } })
            .then(data => window.location.reload())
            .catch(error => {
                errorHandler.handleWithHistoryObject(history,error);
            });
        toggle();
    }

    return (
        <div>
            <div onClick={toggle}><b>Üye:</b>{props.groupMembers.length}</div>
            <Modal isOpen={modal} toggle={toggle} className={className}>
                <ModalHeader toggle={toggle}>Grup üyeleri</ModalHeader>
                <ModalBody>
                    {
                        props.groupMembers.length > 0
                        ?
                        (
                            <p>{props.groupMembers.length} adet üye bulundu..</p>
                        )
                        :
                        (
                            <p>Hiç üyeniz yok!</p>
                        )
                    } 
                    <ul className={groupStyle.joinRequests}>
                    <div className={groupStyle.jrhr}></div>
                        {
                            props.groupMembers.map((item, index) => (
                                <li className={groupStyle.joinRequest}>
                                    <div className={groupStyle.jrUserPhoto}>
                                        <img alt="" src={item.photo ? item.photo : "/nophoto.jpg"} />
                                    </div>
                                    <div className={groupStyle.jrInfo}>
                                        <b>{item.username}</b>
                                        <p>{item.firstname} {item.lastname}</p>
                                    </div>
                                    {
                                        props.myRole === 3
                                        &&
                                        (
                                            <div className={groupStyle.kickButtons}>
                                                <div onClick={() => kickUser(item)} className={groupStyle.jrDenyButton}>
                                                    <FontAwesomeIcon icon={faTimes} />
                                                </div>
                                            </div>
                                        )
                                    }
                                </li>
                            ))
                        }
                    </ul>
                </ModalBody>
            </Modal>
        </div>
    );
}

export default ModalShowMembers;