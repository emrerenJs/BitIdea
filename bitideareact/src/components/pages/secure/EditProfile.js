import axios from 'axios';
import React, { Component } from 'react'
import { Redirect } from 'react-router';
import editProfileStyle from '../../../styles/css/editProfile.module.css'

export default class EditProfile extends Component {
    constructor(props) {
        super(props);
        this.state = {
            redirect: {
                option: false,
                page: null,
                message: null
            },
            profile: {
                username: this.props.match.params.username,
                firstname: "",
                lastname: "",
                photo: null,
                biography: "",
                fileToUpload:null
            }
        }
    }

    componentDidMount() {
        this.setState({
            profile: JSON.parse(localStorage.getItem("profile"))
        });
    }

    inputChangeHandler(e) {
        const { value, name } = e.target;
        const { profile } = this.state;
        profile[name] = value;
        this.setState({
            profile
        });
    }

    editProfileHandler() {
        const config = {
            headers: {
                Authorization: `Bearer ${localStorage.getItem('token')}`,
                "content-type":"multipart/form-data"
            }
        };
        let formData = new FormData();
        formData.append('file',this.state.profile.fileToUpload);
        formData.append('firstname',this.state.profile.firstname);
        formData.append('lastname',this.state.profile.lastname);
        formData.append('biography',this.state.profile.biography);
        formData.append('photo',this.state.profile.photo);        
        axios
            .post("/profile/updateProfile", formData, config)
            .then(response => {
                localStorage.setItem("profile",JSON.stringify(response.data));
                this.props.history.replace(`/@${this.state.profile.username}`);
            })
            .catch(error => {
                if (error.response.status === 403) {
                    this.props.history.replace('/login');
                } else {
                    this.props.history.replace("/500");
                }

            });
    }

    uploadPhotoOnClickHandler(){
        document.getElementById("uploadPhoto").click();
    }

    photoSelectedHandler(e){
        const newProfile = this.state.profile;
        const reader = new FileReader();
        newProfile.fileToUpload = e.target.files[0];
        reader.onload = (e) => {
            newProfile.photo = e.target.result;
            this.setState({
                profile:newProfile
            });
        }
        reader.readAsDataURL(e.target.files[0]);
    }

    render() {
        if (this.state.redirect.option) {
            return (
                <Redirect to={this.state.redirect.page} />
            )
        } else {
            return (
                <div className={editProfileStyle.appContainer}>
                    <div className={editProfileStyle.editForm}>
                        <div className={editProfileStyle.photoInput}>
                            <div className={editProfileStyle.photo}>
                                <img alt="" src={this.state.profile.photo ? this.state.profile.photo : "nophoto.jpg"} />
                            </div>
                            <div className={editProfileStyle.uploadPhotoButton} onClick={this.uploadPhotoOnClickHandler.bind(this)}>
                                Fotoğraf seç
                            </div>
                            <input id="uploadPhoto" onChange={this.photoSelectedHandler.bind(this)} type="file" style={{display:'none'}} accept="image/png, image/jpeg"/>
                        </div>
                        <div className={editProfileStyle.firstlastnameInput}>
                            <input onChange={this.inputChangeHandler.bind(this)} name="firstname" placeholder="İsim" value={this.state.profile.firstname} type="text" />
                            <input onChange={this.inputChangeHandler.bind(this)} name="lastname" placeholder="Soyisim" value={this.state.profile.lastname} type="text" />

                        </div>
                        <div className={editProfileStyle.biographyInput}>
                            <textarea onChange={this.inputChangeHandler.bind(this)} value={this.state.profile.biography} placeholder="Biyografi.." name="biography"></textarea>
                        </div>
                        <div className={editProfileStyle.sendInput}>
                            <div onClick={this.editProfileHandler.bind(this)} className={editProfileStyle.sendButton}>
                                Güncelle
                            </div>
                        </div>
                    </div>
                </div>
            )
        }
    }
}
