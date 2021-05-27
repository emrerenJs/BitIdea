import React, { useState } from 'react';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Input, Row, Col, FormGroup, Label } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPlus } from '@fortawesome/free-solid-svg-icons';
import createChallangeStyle from '../../../styles/css/createChallange.module.css';

const ModalAddNewParameter = (props) => {

    const {
        className
    } = props;


    const [modal, setModal] = useState(false);
    const [dimension, setDimension] = useState([1, 1, 1]);
    const [activePage,setActivePage] = useState(1);
    const [variableType,setVariableType] = useState("Integer");

    const toggle = () => {
        setModal(!modal);
        setDimension([1,1,1]);
        setActivePage(1);
        setVariableType("Integer");
    }

    const nextPage = () => activePage < dimension[0] && setActivePage(activePage+1)
    
    const previousPage = () => activePage > 1 && setActivePage(activePage-1)

    const typeOnChangeHandler = e => {
        setVariableType(e.target.value);
        clearAllInputs();
    }

    const typeFilter = e => {
        if(variableType === "Integer" || variableType === "Double" ){
            if(e.target.value.length > 1 && e.target.value.endsWith(".") && !e.target.value.endsWith("..") && variableType === "Double"){
                return;
            }
            let v = 0;
            if(variableType === "Integer"){
                v = parseInt(e.target.value);
            }else{
                v = parseFloat(e.target.value);
            }
            
            if(typeof v === 'number' && !isNaN(v)){
                if(Number.isInteger(v) && variableType === "Integer"){
                    e.target.value = v;
                }else if(variableType === "Double"){
                    e.target.value = v;
                }else{
                    e.target.value = "";
                }
            }else{
                e.target.value = "";
            }
        }else if(variableType === "Char"){
            if(e.target.value.length > 1){
                e.target.value = e.target.value.substring(0,1);
            }
        }
    }
    const finishFilter = (e) => {
        if(variableType === "Double"){
            e.target.value = parseFloat(e.target.value);
        }else if(variableType === "Boolean"){
            if(e.target.value.toLowerCase() == "true" || e.target.value.toLowerCase() === "false"){
                
            }else{
                e.target.value = "";
            }
        }
    }

    const dimensionOnChangeHandler = e => {
        if(e.target.value === "Değişken(skaler)"){
            setDimension([1,1,1]);
        }else if(e.target.value === "Dizi(1d)"){
            const x = parseInt(window.prompt("X ekseni boyutu:"));
            setDimension([1,1,x]);
        }else if(e.target.value === "Matris(2d)"){
            const x = parseInt(window.prompt("X ekseni boyutu:"));
            const y = parseInt(window.prompt("Y ekseni boyutu:"));
            setDimension([1,y,x]);
        }else if(e.target.value === "Tensör(3d)"){
            const x = parseInt(window.prompt("X ekseni boyutu:"));
            const y = parseInt(window.prompt("Y ekseni boyutu:"));
            const z = parseInt(window.prompt("Z ekseni boyutu:"));
            setDimension([z,y,x]);
        }
        clearAllInputs();
    }

    const clearAllInputs = () => {
        for(let z = 0; z < dimension[0]; z++){
            for(let y = 0; y < dimension[1]; y++){
                for(let x = 0; x < dimension[2]; x++){
                    document.getElementById(`scenario ${props.scenarioIndex} input ${z} ${y} ${x}`).value = "";
                }
            }
        }
    }

    const prepareAndAddParameter = () => {
        let globalDimension = 0;
        for(let i = 0; i < dimension.length; i++){
            if(dimension[i] > 1){
                globalDimension++;
            }
        }
        let value;
        if(globalDimension === 0){
            value = document.getElementById(`scenario ${props.scenarioIndex} input 0 0 0`).value;
        }else if(globalDimension === 1){
            value = [];
            for(let x = 0; x < dimension[2]; x++){
                value.push(document.getElementById(`scenario ${props.scenarioIndex} input 0 0 ${x}`).value);
            }
        }else if(globalDimension === 2){
            value = [];
            for(let y = 0; y < dimension[1]; y++){
                value.push([]);
                for(let x = 0; x < dimension[2]; x++){
                    value[y].push(document.getElementById(`scenario ${props.scenarioIndex} input 0 ${y} ${x}`).value);
                }
            }
        }else if(globalDimension === 3){
            value = [];
            for(let z = 0; z < dimension[0]; z++){
                value.push([]);
                for(let y = 0; y < dimension[1]; y++){
                    value[z].push([]);
                    for(let x = 0; x < dimension[2]; x++){
                        value[z][y].push(document.getElementById(`scenario ${props.scenarioIndex} input ${z} ${y} ${x}`).value);
                    }
                }
            }
        }
        const parameter = {
            dimension:globalDimension,
            type:variableType,
            value            
        }

        props.addNewParameter(props.scenarioIndex,parameter);
        toggle();
    }

    const getInputs = () => (
        <div>
            {
                [...Array(dimension[0])].map((z, iz) => (
                    <div className={[createChallangeStyle.parameterPages,iz === activePage - 1 ? createChallangeStyle.parameterPageActive : createChallangeStyle.parameterPagePassive].join(' ')}>
                        {
                            [...Array(dimension[1])].map((y, iy) => (
                                <div className={createChallangeStyle.parameterInputs}>
                                    {
                                        [...Array(dimension[2])].map((x, ix) => (
                                            <FormGroup className={createChallangeStyle.formGroupP}>
                                                <Label>{iy}.{ix}. Parametre:</Label>
                                                <Input onBlur={finishFilter} onChange={typeFilter} id={["scenario",props.scenarioIndex,"input",iz,iy,ix].join(' ')} type="text" />
                                            </FormGroup>
                                        ))
                                    }
                                </div>
                            ))
                        }
                    </div>
                ))
            }
            <div className={createChallangeStyle.pageChange}>
                <div className={[createChallangeStyle.changeButton, createChallangeStyle.previousButton].join(' ')} onClick={previousPage}>
                    
                </div>
                <div>
                    {activePage}
                </div>
                <div className={[createChallangeStyle.changeButton,createChallangeStyle.nextButton].join(' ')} onClick={nextPage}>
                    
                </div>
            </div>
        </div>
    )

    return (
        <li>
            <div className={createChallangeStyle.addButton} onClick={toggle}>
                <div
                    className={createChallangeStyle.addButtonInside}>
                    <FontAwesomeIcon icon={faPlus} className={createChallangeStyle.addIcon} />
                </div>
            </div>
            <Modal isOpen={modal} toggle={toggle} className={className}>
                <ModalHeader toggle={toggle}>Parametre Ekle</ModalHeader>
                <ModalBody>
                    <Row>
                        <Col md={6}>
                            <FormGroup>
                                <Label>
                                    Boyut:
                                </Label>
                                <Input onChange={dimensionOnChangeHandler} type="select">
                                    <option name="0d">Değişken(skaler)</option>
                                    <option name="1d">Dizi(1d)</option>
                                    <option name="2d">Matris(2d)</option>
                                    <option name="3d">Tensör(3d)</option>
                                </Input>
                            </FormGroup>
                        </Col>
                        <Col md={6}>
                            <FormGroup>
                                <Label>
                                    Tip:
                                </Label>
                                <Input onChange={typeOnChangeHandler} type="select">
                                    <option>Integer</option>
                                    <option>Double</option>
                                    <option>Char</option>
                                    <option>Boolean</option>
                                    <option>String</option>
                                </Input>
                            </FormGroup>
                        </Col>
                    </Row>
                    {getInputs()}
                </ModalBody>
                <ModalFooter>
                    <Button color="primary" onClick={prepareAndAddParameter}>Ekle</Button>{' '}
                </ModalFooter>
            </Modal>
        </li>
    );
}

export default ModalAddNewParameter;