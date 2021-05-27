import React, { Component } from 'react'
import { Button, Input, Modal, ModalHeader, ModalBody, ModalFooter } from 'reactstrap';
import addPostElementModalStyle from '../../../styles/css/addPostElementModal.module.css'
import { faList } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { CKEditor } from '@ckeditor/ckeditor5-react';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';

export default class AddPostElementModal extends Component {

    constructor(props) {
        super(props);
        this.state = {

        }
    }

    temp = () => {
        return (
            <div>
                <div className={addPostElementModalStyle.textStyleBox}>
                    <div className={addPostElementModalStyle.textBold}>
                            B
                    </div>
                    <div className={addPostElementModalStyle.textItalic}>
                            I
                    </div>
                    <div className={addPostElementModalStyle.textUnderline}>
                            A
                    </div>
                    <div className={addPostElementModalStyle.textList}>
                        <FontAwesomeIcon icon={faList}/>
                    </div>
                </div>
                <div className={addPostElementModalStyle.textArea}>
                    <Input type="textarea" style={{height:"300px",resize:'none'}}/>
                </div>
            </div>

        )
    }

    render() {
        const { toggleAddElementModal, addElementModalState } = this.props;
        return (
            <Modal size={"lg"} isOpen={addElementModalState} toggle={() => toggleAddElementModal()}>
                <ModalHeader toggle={() => toggleAddElementModal()}>
                    Yeni içerik
                </ModalHeader>
                <ModalBody>

                </ModalBody>
                <ModalFooter>
                    <Button color="success">Kaydet</Button>
                    <Button color="secondary" onClick={() => toggleAddElementModal()}>İptal</Button>
                </ModalFooter>
            </Modal>
        )
    }
}
