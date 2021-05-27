import axios from 'axios';
import React from 'react'
import { Redirect, Route } from 'react-router'

const isAuthenticated = async (props) => {
    if(localStorage.getItem('token')){
        const config = {
            headers : {
                Authorization : `Bearer ${localStorage.getItem('token')}`
            }
        }
        const result = await axios
            .post("/security/isAuthenticated",{},config)
            .then((data) => {
                return true;
            })
            .catch((error) => {
                localStorage.removeItem('token');
                localStorage.removeItem('username');
                localStorage.removeItem('profile');
                props.history.replace("/login");
                return false;
            });
        return result;
    }else{
        return false;
    }
}

export const PrivateRoute = ({component:Component, ...rest})=> (
    <Route {...rest} render={
        (props) => isAuthenticated(props) 
        ? (<Component {...props}/>) 
        : (<Redirect 
            to={{
                pathname:"/login",
                state:{from:props.location}
            }}/>) 
    }/>
)
