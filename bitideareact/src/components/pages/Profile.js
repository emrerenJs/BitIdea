import axios from 'axios';
import React, { Component } from 'react'

import profileStyle from '../../styles/css/profile.module.css'
import { Redirect } from 'react-router';
import errorHandler from '../tools/errorHandler';

export default class Profile extends Component {
    constructor(props) {
        super(props);
        this.state = {
            redirect: {
                option: false,
                page: null,
                message: null
            },
            profile:{
                username:this.props.match.params.username,
                firstname:"",
                lastname:"",
                photo:null,
                biography:""
            },
            profilePosts:[],
        }
    }
    componentDidMount() {
        axios
            .post('/profile/getProfile', { username: this.props.match.params.username })
            .then(response => response.data)
            .then(data => {
                this.setState({
                    profile:data.profile,
                    profilePosts:data.profilePosts
                })
            })
            .catch(error => {
                this.setState({redirect:errorHandler.handleWithRedirectObject(error)});
            });

    }

    goToPost(postId){
        this.props.history.push({
            pathname:`/post/@${postId}`
        });
    }

    getPostText = (post) => {
        //tüm p'leri getir
        let placeholder = post.content;
        let mined = placeholder.replaceAll(/<[^>]*>/g," ");
        mined = mined.replaceAll(/[ \t]{2,}/g," ");
        mined = mined.replaceAll("&nbsp;","");
        mined = mined.length > 250 ? mined.substring(0,250) + "..." : mined;
        return mined;
    }

    render() {
        if (this.state.redirect.option) {
            return(
                <Redirect to={this.state.redirect.page}/>
            )
        } else {
            return (
                <div className={profileStyle.appContainer}>
                    <div className={profileStyle.headerContainer}>
                        <div className={profileStyle.photoContainer}>
                            <img alt="" src={this.state.profile.photo ? this.state.profile.photo : "nophoto.jpg"} className={profileStyle.photo} />
                        </div>
                        <div className={profileStyle.informations}>
                            <div className={profileStyle.username}>
                                {this.state.profile.username}
                            </div>
                            <div className={profileStyle.interactionContainer}>
                                <div className={profileStyle.interactBtn}>
                                    Takip
                                </div>

                                <div className={profileStyle.interactBtn}>
                                    Mesaj
                                </div>
                            </div>
                            <div className={profileStyle.personal}>
                                <div className={profileStyle.personalName}>{this.state.profile.firstname} {this.state.profile.lastname}</div>
                                <div className={profileStyle.personalBio}>
                                    {this.state.profile.biography}
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className={profileStyle.postContainer}>
                        {
                            this.state.profilePosts.length > 0
                            ? 
                            (
                            <ul className={profileStyle.posts}>
                                {
                                    this.state.profilePosts.map((item,index)=>(
                                        <li key={item.id}>
                                            <div className={profileStyle.coursesContainer}>
                                                <div className={profileStyle.course}>
                                                    <div className={profileStyle.coursePreview}>
                                                        <img alt="" src={item.bigPicture} width="250px" height="160px"/>
                                                    </div>
                                                    <div className={profileStyle.courseInfo}>
                                                        <h6>{item.username}</h6>
                                                        <h2>{item.header}</h2>
                                                        <p>
                                                            {this.getPostText(item)}
                                                        </p>
                                                        <button className={profileStyle.btn} onClick={this.goToPost.bind(this,item.id)}>Devamını Oku</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </li>
                                    ))
                                }
                            </ul>
                            )
                            :
                            (
                                <div></div>
                            )
                        }
                        
                    </div>
                </div>
            )
        }
    }
}
