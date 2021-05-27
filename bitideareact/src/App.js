import { Route, BrowserRouter as Router, Switch } from "react-router-dom";
import Authentication from "./components/pages/Authentication";
import Profile from "./components/pages/Profile";
import Page404 from './components/pages/Page404';
import Navigation from './components/tools/Navigation';
import EditProfile from './components/pages/secure/EditProfile';

import React, { Component } from 'react'
import { PrivateRoute } from "./components/tools/PrivateRoute";
import Page500 from "./components/pages/Page500";
import Main from "./components/pages/Main";
import Test from "./components/pages/Test";
import CreatePost from "./components/pages/secure/CreatePost/CreatePost";
import Post from "./components/pages/Post";
import Group from "./components/pages/GroupPage/Group";
import CreateGroup from "./components/pages/CreateGroupPage/CreateGroup"
import CreateChallange from "./components/pages/CreateChallangePage/CreateChallange";
import Challange from './components/pages/ChallangePage/Challange';

class App extends Component {
  render() {
    return (
      <Router>
        <div>
          <Navigation />
          <Switch>
            <Route exact path={"/"} component={Main}/>
            <Route exact path={"/home"} component={Main}/>
            <Route path={"/login"} component={Authentication} />
            <Route path={"/register"} component={Authentication} />
            <Route path={"/@:username"} component={Profile} />
            <Route path={"/post/@:postid"} component={Post} />
            <Route path={"/group/@:groupName"} component={Group} />
            <PrivateRoute path={"/challange/@:challangeid"} component={Challange}/>
            <PrivateRoute exact path="/editProfile" component={EditProfile} />
            <PrivateRoute exact path="/createPost" component={CreatePost} />
            <PrivateRoute exact path="/createGroup" component={CreateGroup} />
            <PrivateRoute exact path="/createChallange" component={CreateChallange} />
            <Route path={"/500"} component={Page500} />
            <Route path={"/test"} component={Test} />
            <Route path={"/**"} component={Page404} />
          </Switch>
        </div>
      </Router>
    )
  }
}

export default App;
