import React, { Component } from 'react'
import mainStyle from '../../styles/css/main.module.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faSearch } from '@fortawesome/free-solid-svg-icons'


export default class Main extends Component {
    render() {
        return (
            <div className={mainStyle.appContainer}>
                <img alt="" src="/logowithText2.png" className={mainStyle.logo} />
                <div className={mainStyle.searchBar}>
                    <div className={mainStyle.comboArea}>
                        <select className={mainStyle.comboBox} name="searchType">
                            <option value="post">Gönderi</option>
                            <option value="question">Soru</option>
                            <option value="challange">Yarışma</option>
                        </select>
                    </div>
                    <div className={mainStyle.searchInput}>
                        <input type="text" placeholder="Kafanda ne var?" name="search" />
                    </div>
                    <div className={mainStyle.searchButton}>
                        <FontAwesomeIcon icon={faSearch}/>
                    </div>
                </div>
            </div>
        )
    }
}
