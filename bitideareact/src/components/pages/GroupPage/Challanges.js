import React from 'react'

import groupStyle from '../../../styles/css/group.module.css';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import { faPaperPlane } from '@fortawesome/free-solid-svg-icons'
import { useHistory } from 'react-router-dom';

export default function Challanges(props) {
    const history = useHistory();
    const goChallange = (id) => {
        history.push(`/challange/@${id}`);
    }
    return (
        <ul className={groupStyle.challangesContainer}>
            {
                props.challangeList.map((item, index) => (
                    <li onClick={() => goChallange(item.id)} key={index}>
                        {item.challangeHeader}
                    </li>
                ))
            }
        </ul>
    )
}
