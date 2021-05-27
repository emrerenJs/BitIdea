import React, { Component } from 'react'
import axios from 'axios';

import groupStyle from '../../../styles/css/group.module.css';
import { Redirect } from 'react-router';
import GroupInformations from './GroupInformations';
import Announcements from './Announcements';
import Challanges from './Challanges';

import errorHandler from '../../tools/errorHandler';

import { confirmAlert } from 'react-confirm-alert';
import 'react-confirm-alert/src/react-confirm-alert.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faDoorClosed } from '@fortawesome/free-solid-svg-icons';

export default class Group extends Component {

    constructor(props) {
        super(props);
        this.state = {
            redirect: {
                option: false,
                page: null,
                message: null
            },
            groupModel: {
                groupAnnouncements: [],
                groupChallanges: [],
                groupMembers: [],
                groupJoinRequests: [],
                groupName: "",
                groupOwner: {},
                myRole: 0
            },
            activeTab: "announcement"
        }
    }

    componentDidMount() {
        const config = {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            }
        }
        const model = {
            "groupName": this.props.match.params.groupName
        }
        axios.post("/group/getGroupByGroupName", model, config)
            .then(response => {
                this.setState({
                    groupModel: response.data
                })
            })
            .catch(error => {
                this.setState({
                    redirect: errorHandler.handleWithRedirectObject(error)
                });
            });
    }

    switchTabs(activeTab) {
        this.setState({
            activeTab
        })
    }

    joinGroup = () => {
        axios
            .post("/group/joinGroup", { groupName: this.state.groupModel.groupName }, { headers: { Authorization: `Bearer ${localStorage.getItem("token")}` } })
            .then(data => {
                this.redirectAfterInteraction(false);
            })
            .catch(error => {
                if (error.response.status === 403) {
                    this.props.history.replace('/login');
                } else {
                    this.props.history.replace("/500");
                }
            });
    }

    acceptGroup = () => {
        axios
            .post("/group/acceptInvite", { groupName: this.state.groupModel.groupName }, { headers: { Authorization: `Bearer ${localStorage.getItem("token")}` } })
            .then(data => {
                this.redirectAfterInteraction(false);
            })
            .catch(error => {
                if (error.response.status === 403) {
                    this.props.history.replace('/login');
                } else {
                    this.props.history.replace("/500");
                }
            });
    }

    denyGroup = () => {
        axios
            .post("/group/denyInvite", { groupName: this.state.groupModel.groupName }, { headers: { Authorization: `Bearer ${localStorage.getItem("token")}` } })
            .then(data => {
                this.redirectAfterInteraction(false);
            })
            .catch(error => {
                if (error.response.status === 403) {
                    this.props.history.replace('/login');
                } else {
                    this.props.history.replace("/500");
                }
            });
    }

    leaveGroup = () => {
        confirmAlert({
            title: 'Gruptan ayrılıyorsunuz!',
            message: 'Gruptan ayrılıyorsunuz.. Kabul ediyor musunuz?',
            buttons: [
                {
                    label: 'Evet',
                    onClick: () => {
                        axios
                            .post("/group/leaveGroup", { groupName: this.state.groupModel.groupName }, { headers: { Authorization: `Bearer ${localStorage.getItem("token")}` } })
                            .then(data => {
                                this.redirectAfterInteraction(true);
                            })
                            .catch(error => {
                                if (error.response.status === 403) {
                                    this.props.history.replace('/login');
                                } else {
                                    this.props.history.replace("/500");
                                }
                            });
                    }
                },
                {
                    label: 'Hayır',
                    onClick: () => "ok"
                }
            ]
        });
    }

    removeGroup = () => {
        confirmAlert({
            title: 'Grubu siliyorsunuz!',
            message: 'Grubu sildiğiniz zaman ilgili tüm verileri de kaybedeceksiniz.. Kabul ediyor musunuz?',
            buttons: [
                {
                    label: 'Evet',
                    onClick: () => {
                        axios
                            .post("/group/removeGroup", { groupName: this.state.groupModel.groupName }, { headers: { Authorization: `Bearer ${localStorage.getItem("token")}` } })
                            .then(data => {
                                this.redirectAfterInteraction(true);
                            })
                            .catch(error => {
                                if (error.response) {
                                    if (error.response.status === 403) {
                                        this.props.history.replace('/login');
                                    } else {
                                        this.props.history.replace("/500");
                                    }
                                } else {
                                    this.props.history.replace("/500");
                                }

                            });
                    }
                },
                {
                    label: 'Hayır',
                    onClick: () => "ok"
                }
            ]
        });
    }

    redirectAfterInteraction = (goHome) => {
        if (goHome) {
            this.props.history.replace("/home");
        } else {
            window.location.reload();
        }
    }



    render() {
        if (this.state.redirect.option) {
            return (
                <Redirect to={this.state.redirect.page} />
            )
        } else {
            return (
                <div>
                    <div className={groupStyle.spaceFromTop}>

                    </div>
                    <GroupInformations
                        groupName={this.state.groupModel.groupName}
                        groupMembers={this.state.groupModel.groupMembers}
                        groupOwner={this.state.groupModel.groupOwner}
                        groupJoinRequests={this.state.groupModel.groupJoinRequests}
                        myRole={this.state.groupModel.myRole}
                        functions={{
                            joinGroup: this.joinGroup,
                            acceptGroup: this.acceptGroup,
                            denyGroup: this.denyGroup,
                            leaveGroup: this.leaveGroup,
                            removeGroup: this.removeGroup
                        }}
                    />
                    {
                        this.state.groupModel.myRole === 2 || this.state.groupModel.myRole === 3
                        ?
                        (
                            <div className={groupStyle.options} style={{marginTop:"25px"}}>
                                <div className={groupStyle.changeTabs}>
                                    <ul>
                                        <li onClick={() => this.switchTabs("announcement")}>
                                            Duyurular
                                        </li>
                                        <li onClick={() => this.switchTabs("challange")}>
                                            Yarışmalar
                                        </li>
                                    </ul>
                                </div>
                            {
                                this.state.activeTab === "announcement"
                                    ?
                                    (
                                        <Announcements
                                            announcementList={this.state.groupModel.groupAnnouncements}
                                            groupName={this.state.groupModel.groupName}
                                        />
                                    )
                                    :
                                    (
                                        <Challanges challangeList={this.state.groupModel.groupChallanges} />
                                    )
                            }
                            </div>
                        )
                        :
                        (
                            <div className={groupStyle.secretContent}>
                                <img src="/logo403.png" alt=""/>
                            </div>
                        )
                    }
                </div>
            )
        }
    }
}
