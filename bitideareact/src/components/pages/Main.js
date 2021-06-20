import React, { Component } from 'react'
import mainStyle from '../../styles/css/main.module.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faSearch } from '@fortawesome/free-solid-svg-icons'


export default class Main extends Component {
    searchButtonOnClickListener(){
        const category = document.getElementById("category").value;
        const key = document.getElementById("key").value;
        this.props.history.push(`/search/${category}/${key}`);
    }
    render() {
        return (
            <div className={mainStyle.appContainer}>
                <img alt="" src="/logowithText2.png" className={mainStyle.logo} />
                <div className={mainStyle.searchBar}>
                    <div className={mainStyle.comboArea}>
                        <select id="category" className={mainStyle.comboBox} name="searchType">
                            <option value="post">Gönderi</option>
                            <option value="group">Grup</option>
                            <option value="challange">Yarışma</option>
                        </select>
                    </div>
                    <div className={mainStyle.searchInput}>
                        <input id="key" type="text" placeholder="Kafanda ne var?" name="search" />
                    </div>
                    <div className={mainStyle.searchButton} onClick={this.searchButtonOnClickListener.bind(this)}>
                        <FontAwesomeIcon icon={faSearch}/>
                    </div>
                </div>
            </div>
        )
    }
}
