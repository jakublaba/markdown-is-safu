import React from 'react';
import './styles/App.css';
import {createBrowserRouter, createRoutesFromElements, Route, RouterProvider} from "react-router-dom";
import AppNavbar from "./components/AppNavbar";
import {useSelector} from "react-redux";
import {IAuthState} from "./types/types";
import Login from "./components/Login";
import Profile from "./components/Profile";
import Register from "./components/Register";
import NoteList from "./components/NoteList";
import NoteEditor from "./components/NoteEditor";

const App: React.FC = () => {
    const authenticated = useSelector<IAuthState, boolean>(authState => authState.authenticated);

    const router = createBrowserRouter(
        createRoutesFromElements(
            <Route element={<AppNavbar/>}>
                <Route path={"/"} element={authenticated ? <Profile/> : <Register/>}/>
                <Route path={"/login"} element={<Login/>}/>
                <Route path={"/notes"} element={<NoteList/>}/>
                <Route path={"/notes/editor/:uuid?"} element={<NoteEditor/>}/>
            </Route>
        )
    );

    return <RouterProvider router={router}/>
};

export default App;
