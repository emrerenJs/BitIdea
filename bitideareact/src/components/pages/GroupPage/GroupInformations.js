import React from 'react'

import groupStyle from '../../../styles/css/group.module.css';
import ModalSendInvite from './ModalSendInvite';
import ModalShowJoinRequests from './ModalShowJoinRequests';
import ModalShowMembers from './ModalShowMembers';

const whoAmI = (props) => {
    if (props.myRole === -1) {
        return (
            <div
                onClick={() => props.functions.joinGroup()}
                className={[groupStyle.infoJoinButton, groupStyle.infoButton].join(' ')}>
                Katıl
            </div>
        )
    }
    else if (props.myRole === 0) {
        return (
            <div>
                <div
                    onClick={() => props.functions.acceptGroup()}
                    className={[groupStyle.infoAcceptButton, groupStyle.infoButton].join(' ')}>
                    Kabul et
                </div>
                <div
                    onClick={() => props.functions.denyGroup()}
                    className={[groupStyle.infoLeaveButton, groupStyle.infoButton].join(' ')}>
                    Reddet
                </div>
            </div>
        )
    }
    else if (props.myRole === 1) {
        return (
            <div className={[groupStyle.infoJoinedButton, groupStyle.infoButton].join(' ')}>
                Isteğiniz beklemede
            </div>
        )

    }
    else if (props.myRole === 2) {
        return (
            <div
                onClick={() => props.functions.leaveGroup()}
                className={[groupStyle.infoLeaveButton, groupStyle.infoButton].join(' ')}>
                Ayrıl
            </div>
        )
    }
    else if (props.myRole === 3) {
        return (
            <div
                onClick={() => props.functions.removeGroup()}
                className={[groupStyle.infoLeaveButton, groupStyle.infoButton].join(' ')}>
                Grubu Sil
            </div>
        )
    }
}

export default function GroupInformations(props) {
    return (
        <div className={groupStyle.infoAppContainer}>
            {
                props.myRole === 3
                &&
                (
                    <div className={groupStyle.infoHeader}>
                        <div className={groupStyle.infoHeaderFlag}>
                            <ModalSendInvite groupName={props.groupName} />
                            <ModalShowJoinRequests groupName={props.groupName} groupJoinRequests={props.groupJoinRequests} />
                        </div>
                    </div>
                )
            }
            <div className={groupStyle.infoGroup}>
                <div className={groupStyle.groupOwner}>
                    <img alt="" src={props.groupOwner.photo} />
                    <div>
                        <h1>{props.groupOwner.username}</h1>
                        {props.groupOwner.firstname} {props.groupOwner.lastname}
                        <p style={{ fontSize: '12px', margin: "0px" }}>(Grup sahibi)</p>
                    </div>
                    <div className={groupStyle.groupName}>
                        {props.groupName.toUpperCase()}
                    </div>
                </div>
            </div>
            <div className={groupStyle.infoInteraction}>
                {whoAmI(props)}
                <div className={groupStyle.infoMembers}>
                    <ModalShowMembers myRole={props.myRole} groupName={props.groupName} groupMembers={props.groupMembers} />
                </div>
            </div>
        </div>
    )
}
