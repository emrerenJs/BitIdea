import React, { Component } from 'react'
import postStyle from '../../../../styles/css/post.module.css'
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faCheck, faTimes, faCamera } from '@fortawesome/free-solid-svg-icons'

import { CKEditor } from '@ckeditor/ckeditor5-react';
import CustomEditor from 'ckeditor5-custom-build/build/ckeditor';

import axios from 'axios';

const editorConfiguration = {
    toolbar:['heading','|','bold','italic','blockQuote', 'link', 'numberedList','bulletedList','imageUpload','insertTable','undo','redo','codeBlock']
}
/* TEST */

class UploadAdapter {
    loader;
    xhr: any;
    constructor(loader: any, token: any) {
      this.loader = loader;
      this.token = token;
    }
  
    upload() {
      return this.loader.file
        .then(file => new Promise((resolve, reject) => {
          this._initRequest();
          this._initListeners(resolve, reject, file);
          this._sendRequest(file);
        }));
    }
  
    // Aborts the upload process.
    abort() {
      if (this.xhr) {
        this.xhr.abort();
      }
    }
  
    _initRequest() {
      const xhr = this.xhr = new XMLHttpRequest();
      xhr.open('POST', '/post/uploadToTemp', true);
      xhr.responseType = 'json';
      xhr.setRequestHeader('Authorization',this.token); // set your token here
      //xhr.setRequestHeader("Content-Type","multipart/form-data")
    }
  
    // Initializes XMLHttpRequest listeners.
    _initListeners(resolve, reject, file) {
      const xhr = this.xhr;
      const loader = this.loader;
      const genericErrorText = `Couldn't upload file: ${file.name}.`;
  
      xhr.addEventListener('error', () => reject(genericErrorText));
      xhr.addEventListener('abort', () => reject());
      xhr.addEventListener('load', () => {
        const response = xhr.response;
  
        if (!response || response.error) {
          return reject(response && response.error ? response.error.message : genericErrorText);
        }
        resolve({
          default: response.body
        });
      });
  
      if (xhr.upload) {
        xhr.upload.addEventListener('progress', evt => {
          if (evt.lengthComputable) {
            loader.uploadTotal = evt.total;
            loader.uploaded = evt.loaded;
          }
        });
      }
    }
  
    // Prepares the data and sends the request.
    _sendRequest(file) {
      // Prepare the form data.
      const data = new FormData();
  
      data.append('body', file);
      this.xhr.send(data);
    }
  
  }

/* TEST */

export default class CreatePost extends Component {
    constructor(props){
        super(props);
        this.state={
            photoContent:null,
            photoContentToUpload:null,
            headerContent:"Merhaba blog yazım!",
            postContent:"<p>Bu benim öyküm!</p>"
        }
    }

    ckeditorOnChange = (event,editor) => {
        const data = editor.getData();
        this.setState({
            postContent:data
        })
    }

    photoSelectedHandler = (e) => {
        const reader = new FileReader();
        this.setState({
            photoContentToUpload:e.target.files[0]
        });
        reader.onload = (e) => {
            this.setState({
                photoContent : e.target.result
            });
        }
        reader.readAsDataURL(e.target.files[0]);
    }

    headerContentOnChange = (e) =>{
        if(e.target.value.length >= 70){
            e.target.style.outline = '1px solid rgba(205,0,0,.5)';
        }else{
            e.target.style.outline = 'none';
            this.setState({
                headerContent:e.target.value
            });
        }
    }

    selectContentPhoto = () => {
        document.getElementById("uploadContentPhoto").click();
    }

    cancelButtonHandler = () => {
        this.props.history.goBack();
    }

    sendButtonHandler = () => {
        if(this.state.photoContent && this.state.postContent.trim() !== "" && this.state.headerContent.trim() !== "" ){
            const config = {
                headers: {
                    Authorization: `Bearer ${localStorage.getItem('token')}`,
                    "content-type":"multipart/form-data"
                }
            };
            let formData = new FormData();
            formData.append('contentPhoto',this.state.photoContentToUpload);
            formData.append('contentHeader',this.state.headerContent);
            formData.append('contentBody',this.state.postContent);
            axios
                .post("/post/createPost", formData, config)
                .then(data => {
                   alert("İçerik başarıyla paylaşıldı");
                   this.props.history.push(`/@${localStorage.getItem('username')}`);
                })
                .catch(error => {
                    if (error.response.status === 403) {
                        this.props.history.replace('/login');
                    } else {
                        console.log(error);
                        this.props.history.replace("/500");
                    }
    
                });
        }else{
            alert("Alanlar boş kalamaz!");
        }
    }

    render() {
        return (
            <div>
                <div className={postStyle.spaceFromTop}>

                </div>
                <div className={postStyle.appContainer}>
                    <div className={postStyle.bigPicture}>
                        {
                            this.state.photoContent
                            &&
                            (
                                <img alt="" src={this.state.photoContent} />
                            )
                        }
                        <FontAwesomeIcon icon={faCamera} className={postStyle.addPhotoButton} onClick={()=>this.selectContentPhoto()}/>
                        <input id="uploadContentPhoto" onChange={this.photoSelectedHandler} type="file" style={{display:'none'}} accept="image/png, image/jpeg"/>
                    </div>
                    <div className={postStyle.postHeader}>
                        <textarea value={this.state.headerContent} onChange={this.headerContentOnChange}/>
                    </div>
                    <div className={postStyle.postBody}>
                    <CKEditor
                            editor={CustomEditor}
                            data={this.state.postContent}
                            config={editorConfiguration}
                            onReady={
                                (eventData) => {
                                    eventData.plugins.get("FileRepository").createUploadAdapter = (loader) => {
                                        return new UploadAdapter(loader,`Bearer ${localStorage.getItem('token')}`);
                                    }
                                }
                            }
                            onChange={this.ckeditorOnChange}
                        />
                    </div>
                    <div className={postStyle.postOperations}>
                        <div className={[postStyle.cancelButton,postStyle.postOperationsButton].join(' ')}>
                            <FontAwesomeIcon icon={faTimes} onClick={()=>this.cancelButtonHandler()}/>
                        </div>
                        <div className={[postStyle.sendButton,postStyle.postOperationsButton].join(' ')}>
                            <FontAwesomeIcon icon={faCheck} onClick={()=>this.sendButtonHandler()}/>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}
