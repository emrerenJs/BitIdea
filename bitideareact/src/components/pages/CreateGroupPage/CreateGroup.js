import axios from 'axios';
import React, { Component } from 'react'
import mainStyle from '../../../styles/css/main.module.css';

export default class CreateGroup extends Component {
    constructor(props){
        super(props);
        this.state = {
            groupName:""
        }
    }
    createGroup = () => {
        axios
            .post("/group/createGroup",{groupName:this.state.groupName},{headers:{Authorization:`Bearer ${localStorage.getItem('token')}`}})
            .then(data => {
                this.props.history.push(`/group/@${this.state.groupName}`);
            })
            .catch(error => {
                if (error.response.status === 403) {
                    this.props.history.replace('/login');
                } else {
                    console.log(error);
                    this.props.history.replace("/500");
                }
            });
    }

    groupNameOnChangeHandler(e){
        this.setState({
            groupName : e.target.value
        });
    }

    render() {
        return (
            <div className={mainStyle.appContainer}>
                <img alt="" src="/logowithText2.png" className={mainStyle.logo} />
                <div className={mainStyle.searchBar} style={{display:'flex', justifyContent:'center', alignItems:'center'}}>
                    <div className={mainStyle.searchInput}>
                        <input type="text" placeholder="Grup ismi.." name="groupName" value={this.state.groupName} onChange={this.groupNameOnChangeHandler.bind(this)}/>
                    </div>
                </div>
                <div 
                    onClick={() => this.createGroup()}
                    style={{cursor:'pointer',borderRadius:'10px', marginTop:'15px', width:'130px', height:'40px', color:'white', fontWeight:'bold', display:'flex', justifyContent:'center', alignItems:'center', backgroundColor:'#599630'}}>
                    Grup Oluştur
                </div>
            </div>
        )
    }
}
