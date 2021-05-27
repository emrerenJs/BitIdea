import React, { useState } from 'react';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEnvelope } from '@fortawesome/free-solid-svg-icons';
import axios from 'axios';
import groupStyle from '../../../styles/css/group.module.css'
import errorHandler from '../../tools/errorHandler';

import {useHistory} from 'react-router-dom';

const ModalSendInvite = (props) => {
  
  const {
    className
  } = props;

  const history = useHistory();

  const [modal, setModal] = useState(false);
  const [username, setUsername] = useState("");

  const toggle = () => setModal(!modal);
  const usernameTextHandler = (e) => setUsername(e.target.value)
  const sendInvite = () => {
    axios
      .post("/group/inviteUser",{groupName:props.groupName,username},{headers:{Authorization:`Bearer ${localStorage.getItem('token')}`}})
      .then(data => {
        toggle();
        alert("Kullanıcı başarıyla davet edildi..");
      })
      .catch(error => {
        errorHandler.handleWithHistoryObject(history,error);
      });
  }

  return (
    <div style={{float:'left'}}>
      <FontAwesomeIcon onClick={toggle} icon={faEnvelope} style={{ fontSize: 30, color: '#74992e', marginRight: '15px', cursor:'pointer' }} />
      <Modal isOpen={modal} toggle={toggle} className={className}>
        <ModalHeader toggle={toggle}>Davet Gönder</ModalHeader>
        <ModalBody>
          <input placeholder="Kullanıcı adı.." type="text" value={username} onChange={usernameTextHandler} className={groupStyle.sendInviteInput}/>
        </ModalBody>
        <ModalFooter>
          <Button color="primary" onClick={sendInvite}>Davet Gönder</Button>{' '}
          <Button color="secondary" onClick={toggle}>İptal</Button>
        </ModalFooter>
      </Modal>
    </div>
  );
}

export default ModalSendInvite;