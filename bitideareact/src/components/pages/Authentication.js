import React, { Component } from 'react'
import authenticationStyle from '../../styles/css/authentication.module.css'

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faUser, faLock, faAt } from '@fortawesome/free-solid-svg-icons'
import axios from 'axios';


class Authentication extends Component {
    constructor(props) {
        super(props);
        this.state = {
            logoPagePosition: false,
            loginModel: {
                username: null,
                password: null
            },
            registerModel: {
                username: null,
                email: null,
                password: null,
                passwordAgain: null
            },
            loginErrors: [],
            registerErrors: [],
            canRequestSendible:true
        }
    }

    componentDidMount() {
        if (this.props.location.pathname === "/register") {
            this.setState({
                logoPagePosition: true
            });
        }
    }

    loginFormChangeHandler(e) {
        const { value, name } = e.target;
        const { loginModel } = this.state;
        loginModel[name] = value;
        this.setState({
            loginModel
        });
    }

    registerFormChangeHandler(e) {
        const { value, name } = e.target;
        const { registerModel } = this.state;
        registerModel[name] = value;
        this.setState({
            registerModel
        });
    }

    async toggleLogoPage(e) {
        
        await this.setState({
            logoPagePosition: !this.state.logoPagePosition,
            registerErrors:[],
            loginErrors:[]            
        });
    }

    async handleRegisterBtn(e) {
        const { registerModel } = this.state;

        await this.setState({
            registerErrors: [],
            canRequestSendible:true
        });

        if (
            registerModel.username === null ||
            registerModel.username.trim() === "" ||
            registerModel.password === null ||
            registerModel.password.trim() === "" ||
            registerModel.passwordAgain === null ||
            registerModel.passwordAgain.trim() === "" ||
            registerModel.email === null ||
            registerModel.email.trim() === ""
        ) {
            const { registerErrors } = this.state;
            registerErrors.push("Alanlar boş kalamaz!");
            this.setState({
                registerErrors,
                canRequestSendible:true
            })
        } else {
            if (registerModel.password !== registerModel.passwordAgain) {
                const { registerErrors } = this.state;
                registerErrors.push("Parolalar uyuşmuyor!");
                this.setState({
                    registerErrors
                });
            } else {
                const emailRegex = /.+[@].+[.].+/
                if (emailRegex.test(registerModel.email)) {
                    /*everything okey!*/
                    axios
                    .post('/security/register',registerModel)
                    .then(response => response.data)
                    .then(data => {this.setState({logoPagePosition:false}); alert("Kaydınız başarılı.. Lütfen e-mail adresinizi kontrol edin!")})
                    .catch(error => {
                        const {registerErrors} = this.state;
                        if(error.response.data.hasOwnProperty('body')){
                            registerErrors.push(error.response.data.body);
                            this.setState({
                                registerErrors
                            });
                        }else{
                            registerErrors.push("Sunucuda beklenmeyen bir hata meydana geldi. Lütfen daha sonra tekrar deneyin.")
                            this.setState({
                                registerErrors
                            });
                        }
                    })
                    .finally(()=>{
                        this.setState({
                            canRequestSendible:true
                        })
                    });
                } else {
                    const { registerErrors } = this.state;
                    registerErrors.push("Lütfen geçerli bir email girin!");
                    this.setState({
                        registerErrors
                    })
                }
            }
        }
        e.preventDefault();

    }

    async handleLoginBtn(e) {
        const { loginModel } = this.state;

        await this.setState({
            loginErrors: [],
            canRequestSendible:false
        });

        if (
            loginModel.username === null ||
            loginModel.username.trim() === "" ||
            loginModel.password === null ||
            loginModel.password.trim() === "") {
            const { loginErrors } = this.state;
            loginErrors.push("Alanlar boş kalamaz!")
            this.setState({
                loginErrors,
                canRequestSendible:true
            });
        } else {
            /*everything okey!*/
            axios
                .post('/security/login',loginModel)
                .then(response => response.data)
                .then(data => {
                    localStorage.setItem("token",data.body);
                    localStorage.setItem("username",loginModel.username);
                    localStorage.setItem("profile",JSON.stringify(data.profile));
                    this.props.history.push({
                        pathname:`/@${loginModel.username}`
                    });
                })
                .catch(error => {
                    const {loginErrors} = this.state;
                    if(error.response.data.hasOwnProperty('body')){
                        loginErrors.push(error.response.data.body);
                        this.setState({
                            loginErrors
                        });
                    }else{
                        loginErrors.push("Sunucuda beklenmeyen bir hata meydana geldi. Lütfen daha sonra tekrar deneyin.")
                        this.setState({
                            loginErrors
                        });
                    }
                })
                .finally(()=>{
                    this.setState({
                        canRequestSendible:true
                    });
                });
        }
        e.preventDefault();
    }

    render() {
        return (
            <div className={authenticationStyle.appContainer}>
                <div className={authenticationStyle.authSquare}>
                    <div id="logoPage" className={[authenticationStyle.logoPage, this.state.logoPagePosition ? authenticationStyle.rightLogoPage : authenticationStyle.leftLogoPage].join(' ')}>
                        <img src="/logowithText2.png" width="200px" height="200px" alt=""/>
                        <span className={authenticationStyle.toggleButton} onClick={this.toggleLogoPage.bind(this)}>
                            {
                                this.state.logoPagePosition
                                    ?
                                    (
                                        <p>Hesabın var mı?</p>
                                    )
                                    :
                                    (
                                        <p>Hesabın yok mu?</p>
                                    )
                            }
                        </span>
                    </div>
                    <div className={authenticationStyle.authFormPage}>
                        <form className={authenticationStyle.authRegisterPage}>
                            <div className={authenticationStyle.inputGroup}>
                                <div>
                                    <FontAwesomeIcon icon={faUser} />
                                </div>
                                <input onChange={this.registerFormChangeHandler.bind(this)} name="username" placeholder="Kullanıcı Adı" type="text" />
                            </div>
                            <div className={authenticationStyle.inputGroup}>
                                <div>
                                    <FontAwesomeIcon icon={faAt} />
                                </div>
                                <input onChange={this.registerFormChangeHandler.bind(this)} name="email" placeholder="E-Posta" type="text" />
                            </div>
                            <div className={authenticationStyle.inputGroup}>
                                <div>
                                    <FontAwesomeIcon icon={faLock} />
                                </div>
                                <input onChange={this.registerFormChangeHandler.bind(this)} name="password" placeholder="Parola" type="password" />
                            </div>
                            <div className={authenticationStyle.inputGroup}>
                                <div>
                                    <FontAwesomeIcon icon={faLock} />
                                </div>
                                <input onChange={this.registerFormChangeHandler.bind(this)} name="passwordAgain" placeholder="Parola Tekrar" type="password" />
                            </div>
                            <span onClick={this.handleRegisterBtn.bind(this)} className={[authenticationStyle.button, this.state.canRequestSendible ? "" : authenticationStyle.disabledButton].join(' ')}> Kaydol </span>
                        </form>
                        <div className={authenticationStyle.registerFooterMessages}>
                            <ul>
                                {this.state.registerErrors.map((error, index) => (
                                    <li className={authenticationStyle.errorMessage} key={index}>{error}</li>
                                ))}
                            </ul>
                        </div>
                    </div>
                    <div className={authenticationStyle.authFormPage}>
                        <form className={authenticationStyle.authLoginPage}>
                            <div className={authenticationStyle.inputGroup}>
                                <div>
                                    <FontAwesomeIcon icon={faUser} />
                                </div>
                                <input onChange={this.loginFormChangeHandler.bind(this)} name="username" placeholder="Kullanıcı Adı" type="text" />
                            </div>
                            <div className={authenticationStyle.inputGroup}>
                                <div>
                                    <FontAwesomeIcon icon={faLock} />
                                </div>
                                <input onChange={this.loginFormChangeHandler.bind(this)} name="password" placeholder="Parola" type="password" />
                            </div>
                            <span className={[authenticationStyle.button, this.state.canRequestSendible ? "" : authenticationStyle.disabledButton].join(' ')} onClick={this.handleLoginBtn.bind(this)}> Giriş Yap </span>
                        </form>
                        <div className={authenticationStyle.loginFooterMessages}>
                            <ul>
                                {this.state.loginErrors.map((error, index) => (
                                    <li key={index} className={authenticationStyle.errorMessage}> {error} </li>
                                ))}
                            </ul>
                        </div>
                    </div>
                </div>
                <div className={authenticationStyle.mobileAuthSquare}>
                    <div className={authenticationStyle.mobileAuthHeader}>
                        <img alt="" src="/logowithText2.png" width="125px" height="125px" />
                    </div>
                    <div className={authenticationStyle.mobileAuthBody}>

                    </div>
                    <div className={authenticationStyle.mobileAuthFooter}>
                        {
                            this.state.logoPagePosition
                                ?
                                (
                                    <div className={authenticationStyle.footerButtons}>
                                        <span className={authenticationStyle.mobileMainButton}>Kayıt Ol</span>
                                        <span className={authenticationStyle.mobilePrimaryButton} onClick={this.toggleLogoPage.bind(this)}>Hesabım var</span>
                                    </div>
                                )
                                :
                                (
                                    <div className={authenticationStyle.footerButtons}>
                                        <span className={authenticationStyle.mobileMainButton}>Giriş Yap</span>
                                        <span className={authenticationStyle.mobilePrimaryButton} onClick={this.toggleLogoPage.bind(this)}>Hesabım yok</span>
                                    </div>
                                )
                        }
                    </div>
                </div>
            </div>
        )
    }
}


export default Authentication;