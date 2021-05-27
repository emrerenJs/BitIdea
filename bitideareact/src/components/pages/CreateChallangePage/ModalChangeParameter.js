import React, { useState } from 'react';
import { Button, Modal, ModalHeader, ModalBody, ModalFooter, Input, Row, Col, FormGroup, Label } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faPlus } from '@fortawesome/free-solid-svg-icons';
import createChallangeStyle from '../../../styles/css/createChallange.module.css';

const ModalChangeParameter = (props) => {

    const {
        className
    } = props;


    const [modal, setModal] = useState(false);
    const [activePage, setActivePage] = useState(1);
    const [maxPage,setMaxPage] = useState(props.parameter.dimension === 3 ? props.parameter.value.length : 1);
    const [valueState,setValueState] = useState(props.parameter.value)

    const toggle = () => {
        setModal(!modal);
        setActivePage(1);
    }

    const nextPage = () => activePage < maxPage && setActivePage(activePage+1)
        
    const previousPage = () => activePage > 1 && setActivePage(activePage-1)

    const setParameterAgain = (v,inputDimension) => {
        v=v.toString();
        if(props.parameter.dimension === 0){
            setValueState(v);             
        }else if(props.parameter.dimension === 1){
            const valueStateM = [...valueState];
            valueStateM[inputDimension[2]] = v;
            setValueState(valueStateM);
        }else if(props.parameter.dimension === 2){
            const valueStateM = [...valueState];
            valueStateM[inputDimension[1]][inputDimension[2]] = v;
            setValueState(valueStateM);
        }else if(props.parameter.dimension === 3){
            const valueStateM = [...valueState];
            valueStateM[inputDimension[0]][inputDimension[1]][inputDimension[2]] = v;
            setValueState(valueStateM);
        }
    }

    const typeFilter = (inputDimensions,e) => {
        const variableType = props.parameter.type;
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
                    setParameterAgain(v,inputDimensions);
                }else if(variableType === "Double"){
                    setParameterAgain(v,inputDimensions);
                }else{
                    setParameterAgain("",inputDimensions);
                }
            }else{
                setParameterAgain("",inputDimensions);
            }
        }else if(variableType === "Char"){
            if(e.target.value.length > 1){
                setParameterAgain(e.target.value.substring(0,1),inputDimensions);
            }
        }else{
            setParameterAgain(e.target.value,inputDimensions);
        }
    }

    const finishFilter = () => {

    }

    const prepareAndChangeParameter = () => {
        const parameter = {
            dimension:props.parameter.dimension,
            type:props.parameter.type,
            value:valueState            
        }
        props.changeParameter(props.scenarioIndex,props.parameterIndex,parameter);
        toggle();
    }

    const getInputs = () => {
        const dimension = [1,1,1];
        if(props.parameter.dimension === 1){
            dimension[2] = props.parameter.value.length;
        }else if(props.parameter.dimension === 2){
            dimension[1] = props.parameter.value.length;
            dimension[2] = props.parameter.value[0].length;
        }else if(props.parameter.dimension === 3){
            dimension[0] = props.parameter.value.length;
            dimension[1] = props.parameter.value[0].length;
            dimension[2] = props.parameter.value[0][0].length;
        }
        return(
            <div>
                {
                    [...Array(dimension[0])].map((z, iz) => (
                        <div className={[createChallangeStyle.parameterPages, iz === activePage - 1 ? createChallangeStyle.parameterPageActive : createChallangeStyle.parameterPagePassive].join(' ')}>
                            {
                                [...Array(dimension[1])].map((y, iy) => (
                                    <div className={createChallangeStyle.parameterInputs}>
                                        {
                                            [...Array(dimension[2])].map((x, ix) => (
                                                <FormGroup className={createChallangeStyle.formGroupP}>
                                                    <Label>{iy}.{ix}. Parametre:</Label>
                                                    <Input 
                                                        value={
                                                            props.parameter.dimension === 0
                                                            ?
                                                            (
                                                                valueState
                                                            )
                                                            :
                                                            (
                                                                props.parameter.dimension === 1
                                                                ?
                                                                (
                                                                    valueState[ix]
                                                                )
                                                                :
                                                                (
                                                                    props.parameter.dimension === 2
                                                                    ?
                                                                    (
                                                                        valueState[iy][ix]
                                                                    )
                                                                    :
                                                                    (
                                                                        props.parameter.dimension === 3
                                                                        ?
                                                                        (
                                                                            valueState[iz][iy][ix]
                                                                        )
                                                                        :
                                                                        (
                                                                            ""
                                                                        )
                                                                    )
                                                                )
                                                            )
                                                        }
                                                        onBlur={finishFilter} 
                                                        onChange={(e) => typeFilter([iz,iy,ix],e)} 
                                                        id={["scenario", props.scenarioIndex, "input", iz, iy, ix].join(' ')} type="text" />
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
                    <div className={[createChallangeStyle.changeButton, createChallangeStyle.nextButton].join(' ')} onClick={nextPage}>

                    </div>
                </div>
            </div>
        )
    }

    return (
        <div className={createChallangeStyle.parameterEditButton} onClick={toggle}>
            o
            <Modal isOpen={modal} toggle={toggle} className={className}>
                <ModalHeader toggle={toggle}>Parametre Değiştir</ModalHeader>
                <ModalBody>
                    {getInputs()}
                </ModalBody>
                <ModalFooter>
                    <Button color="primary" onClick={prepareAndChangeParameter}>Değiştir</Button>{' '}
                </ModalFooter>
            </Modal>
        </div>
    );
}

export default ModalChangeParameter;