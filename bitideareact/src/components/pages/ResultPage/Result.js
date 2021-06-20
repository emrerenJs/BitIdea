import React, { useState, useEffect } from 'react'
import {useHistory} from 'react-router';
import axios from 'axios';

import resultStyle from '../../../styles/css/result.module.css';
import profileStyle from '../../../styles/css/profile.module.css';

export default function Result(props) {

    const [result, setResult] = useState([]);
    const [category, setCategory] = useState("");
    const [key, setKey] = useState("");

    const history = useHistory();

    useEffect(() => {
        const { category, key } = props.match.params;
        setCategory(category)
        setKey(key)
        search(category, key);
    }, [])

    const search = (category, key) => {
        axios.post(
            `/${category}/getByKey`,
            { key }
        )
            .then(response => {
                setResult(response.data)
                console.log(response);
            })
            .catch(error => {
                console.log(error);
            })
    }

    const getFixedText = text => {
        //tüm p'leri getir
        let placeholder = text;
        let mined = placeholder.replaceAll(/<[^>]*>/g," ");
        mined = mined.replaceAll(/[ \t]{2,}/g," ");
        mined = mined.replaceAll("&nbsp;","");
        mined = mined.length > 250 ? mined.substring(0,250) + "..." : mined;
        return mined;
    }

    const goPage = (id) => {
        history.push(`/${category}/@${id}`);
    }

    return (
        <div className={resultStyle.appContainer}>
            <div className={resultStyle.header}>
                <h1 className={resultStyle.headerText}>
                    "{key}" için {result.length} adet sonuç bulundu.. ({category})
                </h1>
            </div>
            <div className={resultStyle.body}>
                <ul className={resultStyle.bodyList}>
                    {
                        result.map((item, index) => (
                            <li key={index}>
                                <div className={profileStyle.coursesContainer}>
                                    <div className={profileStyle.course}>
                                        <div className={profileStyle.coursePreview}>
                                            <img 
                                                alt=""
                                                src={
                                                    category === "post" ? "/" + item.bigPicture : (category === "group" ? "/group.png" : "/challange.png")
                                                } 
                                                width="250px"
                                                height="160px"
                                            />
                                        </div>
                                        <div className={profileStyle.courseInfo}>
                                            <h2>
                                                {
                                                    category === "post" ? item.header : (category === "group" ? item.name : item.challangeHeader) 
                                                }
                                            </h2>
                                            <p>
                                                {
                                                    category === "challange" ? getFixedText(item.challangeBody) : (category === "post" ? getFixedText(item.content) : "")
                                                }
                                            </p>
                                            <button className={profileStyle.btn} onClick={() => goPage(category === "group" ? item.name : item.id)}>Görüntüle</button>
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
