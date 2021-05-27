import React, { Component } from 'react'

import postStyle from '../../styles/css/post.module.css'
import axios from 'axios';
import { Redirect } from 'react-router';
import errorHandler from '../tools/errorHandler';

export default class Post extends Component {

    constructor(props) {
        super(props);
        this.state = {
            postModel: null,
            redirect:{},
            owner: null,
            comment: "",
            user: null
        }
    }

    commentHandler(e) {
        if (e.target.value.length <= 250) {
            this.setState({
                comment: e.target.value
            });
        }
    }

    componentDidMount() {
        axios
            .post('/post/getPost', { postId: this.props.match.params.postid })
            .then(response => response.data)
            .then(data => {
                if(data.post){
                    this.setState({
                        postModel: data.post,
                        owner: data.owner,
                        user: JSON.parse(localStorage.getItem("profile"))
                    });
                }else{
                    const redirectObj = this.state.redirect;
                    redirectObj.option = true;
                    redirectObj.page = "/404";
                    redirectObj.message = "Aranan gönderi bulunamadı";
                    this.setState({
                        redirect: redirectObj
                    });
                }

            })
            .catch(error => {
                this.setState({redirect:errorHandler.handleWithRedirectObject(error)});
            });
    }

    goPostOwnerProfile() {
        this.props.history.push({
            pathname: `/@${this.state.owner.username}`
        });
    }

    sendCommentHandler(e) {
        const config = {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`
            }
        };
        if(this.state.comment.trim() !== ""){
            axios
            .post("/post/newComment", {comment:this.state.comment,postId:this.state.postModel.id},config)
            .then(data => window.location.reload());
        }
    }

    render() {
        if (this.state.redirect.option) {
            return(
                <Redirect to={this.state.redirect.page}/>
            )
        } else {
            return (
                this.state.postModel
                    ?
                    <div>
                        <div className={postStyle.spaceFromTop}>
    
                        </div>
                        <div className={postStyle.postOwner} onClick={this.goPostOwnerProfile.bind(this)}>
                            <div className={postStyle.postOwnerPic}>
                                <img alt="" src={this.state.owner.photo ? this.state.owner.photo : "/nophoto.jpg"} />
                            </div>
                            <div className={postStyle.postOwnerUsername}>
                                <b>{this.state.owner.username}</b>
                                <p>
                                    {this.state.owner.biography}
                                </p>
                            </div>
                        </div>
                        <div className={postStyle.appContainer}>
                            <div className={postStyle.bigPicture}>
                                <img alt="" src={"http://localhost:8080/" + this.state.postModel.bigPicture} />
                            </div>
                            <div className={postStyle.postHeader}>
                                <h1 style={{ fontWeight: 'bold' }}>{this.state.postModel.header}</h1>
                            </div>
                            <div className={postStyle.postBody} dangerouslySetInnerHTML={{ __html: this.state.postModel.content }} />
                            <div className={postStyle.postComments}>
                                <div className={postStyle.sendComment}>
                                    <div className={postStyle.yourPhoto}>
                                        <img 
                                            alt=""
                                            src={this.state.user.photo ? this.state.user.photo : "/nophoto.jpg"}
                                        />
                                    </div>
                                    <div className={postStyle.commentArea}>
                                        <textarea value={this.state.comment} onChange={this.commentHandler.bind(this)} name="userComment" placeholder="Yorumunuz buraya.." />
                                    </div>
                                    <div onClick={this.sendCommentHandler.bind(this)} className={postStyle.submitButton}>
                                        Gönder
                                    </div>
                                </div>
                                <div className={postStyle.commentDivider}>
                                    <p>
                                        {this.state.postModel.postComments.length} Yorum..
                                </p>
                                </div>
                                <div className={postStyle.sendedComments}>
                                {this.state.postModel.postComments.map((item, index) => (
                                        <div key={index} className={postStyle.sendedCommentArea}>
                                            <div className={postStyle.commentDate}>
                                                {item.date}    
                                            </div>
                                            <div className={postStyle.sendedComment}>
                                                <div className={postStyle.commentPhoto}>
                                                    <img alt="" src={item.senderPhoto ? item.senderPhoto : "/nophoto.jpg"} />
                                                </div>
                                                <div className={postStyle.commentBody}>
                                                    <div className={postStyle.commentOwner}>
                                                        {item.senderUsername}
                                                    </div>
                                                    <div className={postStyle.comment}>
                                                        {item.comment}
                                                    </div>
                                                </div>
                                            </div>
                                            <div className={postStyle.commentsDivider}>
    
                                            </div>
                                        </div>
                                    ))}
                                </div>
                            </div>
                        </div>
                    </div>
                    :
                    <div>
                        Bekle
                </div>
            )
        }
    }
}
