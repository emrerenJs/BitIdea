import React,{useState} from 'react'

import groupStyle from '../../../styles/css/group.module.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faPaperPlane } from '@fortawesome/free-solid-svg-icons'
import errorHandler from '../../tools/errorHandler';
import axios from 'axios';
import {useHistory} from 'react-router-dom';
import moment from 'moment';


export default function Announcements(props) {

    const profile = JSON.parse(localStorage.getItem("profile"));
    const history = useHistory();
    const [announcementInput,setAnnouncementInput] = useState("");
    
    const handleAnnouncementInput = (e) => setAnnouncementInput(e.target.value);

    const shareAnnouncement = () => {
        if(announcementInput.trim() === ""){
            alert("Duyuru bölümü boş geçilemez!");
        }else{
            axios
            .post(
                "/group/addAnnouncement",
                {groupName:props.groupName, announcement:announcementInput},
                {headers:{Authorization:`Bearer ${localStorage.getItem('token')}`}}
            )
            .then(data => window.location.reload())
            .catch(error => errorHandler.handleWithHistoryObject(history,error));
        }
    }

    const shareComment = (announcementId) => {
        const commentInput = document.querySelector("#commentInput" + announcementId);
        if(commentInput.value.trim() === ""){
            alert("Yorum boş geçilemez!");
        }else{
            axios
                .post(
                    "/group/addCommentToAnnouncement",
                    {
                        groupAnnouncementId:announcementId,
                        groupName:props.groupName,
                        comment:commentInput.value
                    },
                    {
                        headers:{
                            Authorization:`Bearer ${localStorage.getItem('token')}`
                        }
                    }
                )
                .then(data => window.location.reload())
                .catch(error => errorHandler.handleWithHistoryObject(history,error));
        }
    }


    return (
        <div className={groupStyle.announcementsComponent}>
            <div className={groupStyle.announcementsContainer}>
                <div className={groupStyle.shareAnnouncement}>
                    <div className={groupStyle.profilePicture}>
                        <img src={profile.photo ? profile.photo : "/nophoto.jpg"} alt="" />
                    </div>
                    <div className={groupStyle.shareAnnouncementInput}>
                        <input type="text" value={announcementInput} onChange={handleAnnouncementInput} placeholder="Duyurunuzu yazın.." />
                        <div onClick={shareAnnouncement}>
                            <FontAwesomeIcon icon={faPaperPlane} style={{ fontSize: 30 }} />
                        </div>
                    </div>
                </div>
                <ul className={groupStyle.announcements}>
                    {
                        props.announcementList.map((item, index) => (
                            <li className={groupStyle.announcementContainer} key={index}>
                                <div className={groupStyle.announcementSelf}>
                                    <div className={groupStyle.announcerHeader}>
                                        <div className={groupStyle.announcerPhoto}>
                                            <img src={item.announcer.photo ? item.announcer.photo : "/nophoto.jpg"} alt="" />
                                        </div>
                                        <div className={groupStyle.announcerInfo}>
                                            <b>{item.announcer.username}</b>
                                            <p>{item.createdAt}</p>
                                        </div>
                                    </div>
                                    <div className={groupStyle.announcement}>
                                        {item.announcement}
                                    </div>
                                </div>
                                <div className={groupStyle.toggleComments}>
                                    
                                </div>
                                <div className={groupStyle.announcementComments}>
                                    <ul>
                                        {
                                            item.comments.map((comment, cIndex) => (
                                                <li key={cIndex} className={groupStyle.announcementComment}>
                                                    <div className={groupStyle.commentInfo}>
                                                        <img src={comment.commentOwner.photo ? comment.commentOwner.photo : "/nophoto.jpg"} />
                                                        <b>{comment.commentOwner.username}</b>
                                                        <p>{comment.createdAt}</p>
                                                    </div>
                                                    <div className={groupStyle.comment}>
                                                        {comment.comment}
                                                    </div>
                                                </li>
                                            ))
                                        }
                                    </ul>
                                    <div className={groupStyle.announcementSendComment}>
                                        <div className={groupStyle.commentProfilePicture}>
                                            <img src={profile.photo ? profile.photo : "/nophoto.jpg"} alt="" />
                                        </div>
                                        <div className={groupStyle.commentInput}>
                                            <input id={"commentInput" + item.id} type="text" placeholder="Yorumunuzu yazın.." />
                                            <div onClick={() => shareComment(item.id)}>
                                                <FontAwesomeIcon icon={faPaperPlane} style={{ fontSize: 30 }} />
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </li>
                        ))
                    }
                </ul>
            </div>
        </div>
    )
}
