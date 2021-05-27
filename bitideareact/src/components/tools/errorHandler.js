const handleWithRedirectObject = (error) => {
    const redirectObj = {};
    if(error.response.status === 403){
        redirectObj.option = true;
        redirectObj.page = "/login";
        redirectObj.message = "Giriş yapmalısınız..";
        localStorage.removeItem("username");
        localStorage.removeItem("token");
        localStorage.removeItem("profile");
    }else if(error.response.status === 404){
        redirectObj.option = true;
        redirectObj.page = "/404";
        redirectObj.message = "Aradığınız kayıt bulunamadı..";
    }else{
        redirectObj.option = true;
        redirectObj.page = "/500";
        redirectObj.message = "Sistemde bir hata meydana geldi..";
    }
    return redirectObj;
}

const handleWithHistoryObject = (history, error) => {
    if(error.response.status === 403){
        history.replace("/login");
        localStorage.removeItem("username");
        localStorage.removeItem("token");
        localStorage.removeItem("profile");
    }else if(error.response.status === 404){
        history.push("/404");
    }else{
        if(error.response.data.hasOwnProperty('body')){
            console.log("manual");
            history.push("/500");//handle error here
        }else{
            history.push("/500");
        }
    }
}

module.exports = {handleWithRedirectObject, handleWithHistoryObject};