import React, { Component } from 'react'
import navigationStyle from '../../styles/css/navigation.module.css';

import {
    Collapse,
    Navbar,
    NavbarToggler,
    NavbarBrand,
    Nav,
    UncontrolledDropdown,
    DropdownToggle,
    DropdownMenu,
    DropdownItem
} from 'reactstrap';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faUserCircle } from '@fortawesome/free-solid-svg-icons'

import { withRouter } from 'react-router-dom';


class Navigation extends Component {
    constructor(props) {
        super(props);
        this.state = {
            isOpen: false
        }
    }
    toggle() {
        this.setState({
            isOpen: !this.state.isOpen
        });
    }

    goLoginPage() {
        this.props.history.replace("/login");
    }

    logout() {
        localStorage.removeItem('token');
        localStorage.removeItem('username');
        localStorage.removeItem('profile');
        this.goLoginPage();
    }
    profile() {
        this.props.history.push({
            pathname: `/@${localStorage.getItem('username')}`
        });
    }
    editProfile() {
        this.props.history.push({
            pathname: `/editProfile`
        });
    }
    createPost(){
        this.props.history.push({
            pathname:`/createPost`
        });
    }
    createGroup(){
        this.props.history.push({
            pathname:'/createGroup'
        });
    }
    createChallange(){
        this.props.history.push({
            pathname:'/createChallange'
        });
    }

    getUserBar() {
        if (localStorage.getItem('token') == null) {
            return (
                <UncontrolledDropdown className={["ml-auto", navigationStyle.profileTab].join(' ')} nav inNavbar right={"true"}>
                    <DropdownToggle nav onClick={this.goLoginPage.bind(this)}>
                        <FontAwesomeIcon icon={faUserCircle} style={{ fontSize: 30 }} />
                    </DropdownToggle>
                </UncontrolledDropdown>
            )
        } else {
            const profileStr = localStorage.getItem("profile");
            let photo = "/nophoto.jpg";
            if(profileStr){
                const prof = JSON.parse(profileStr);
                if(prof.photo){
                    photo = prof.photo;
                }
            }
            return (
                <UncontrolledDropdown className={["ml-auto", navigationStyle.profileTab].join(' ')} nav inNavbar right={"true"}>
                    <DropdownToggle nav>
                        <img alt="" src={photo} className={navigationStyle.littlepic}/>
                    </DropdownToggle>
                    <DropdownMenu right>
                        <DropdownItem onClick={this.profile.bind(this)}>
                                Profil
                        </DropdownItem>
                        <DropdownItem onClick={this.editProfile.bind(this)}>
                                Profil düzenle
                        </DropdownItem>
                        <DropdownItem onClick={this.createPost.bind(this)}>
                                Yeni gönderi
                        </DropdownItem>
                        <DropdownItem onClick={this.createGroup.bind(this)}>
                                Yeni grup
                        </DropdownItem>
                        <DropdownItem onClick={this.createChallange.bind(this)}>
                                Yeni yarışma
                        </DropdownItem>
                        <DropdownItem onClick={this.logout.bind(this)}>
                                Çıkış
                        </DropdownItem>
                    </DropdownMenu>
                </UncontrolledDropdown>
            )
        }
    }
    render() {
        return (
            <div className={navigationStyle.appContainer}>
                <Navbar light expand="md" className={navigationStyle.nav}>
                    <NavbarBrand href="/">
                        <img alt="" src={"/logo192.png"} width={"50px"} />
                    </NavbarBrand>
                    <NavbarToggler onClick={this.toggle.bind(this)} />
                    <Collapse isOpen={this.state.isOpen} navbar>
                        <Nav className="mr-auto" navbar>
                        </Nav>
                        <Nav className="ml-auto" navbar>
                            {this.getUserBar()}
                        </Nav>
                    </Collapse>
                </Navbar>
            </div>
        )
    }
}


export default withRouter(Navigation);