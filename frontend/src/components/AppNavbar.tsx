import React from "react";
import {useDispatch, useSelector} from "react-redux";
import {IAction, IAuthState} from "../types/redux-types";
import {NavLink, Outlet, useNavigate} from "react-router-dom";
import {Button, Navbar} from "react-bootstrap";
import {Dispatch} from "redux";

const AppNavbar: React.FC = () => {
    const authenticated = useSelector<IAuthState, boolean>(authState => authState.authenticated);
    const dispatch = useDispatch<Dispatch<IAction>>();
    const navigate = useNavigate();

    const logout = () => {
        dispatch({
            type: "logout",
            jwt: ""
        });
        navigate("/login");
    };

    return (
        <>
            <Navbar className={"nav"}>
                {authenticated ? (
                    <>
                        <NavLink className={"nav-link"} to={"/"}>Profile</NavLink>
                        <NavLink className={"nav-link"} to={"/notes"}>Notes</NavLink>
                        <Button onClick={logout}>Logout</Button>
                    </>
                ) : (
                    <>
                        <NavLink className={"nav-link"} to={"/"}>Register</NavLink>
                        <NavLink className={"nav-link"} to={"/login"}>Login</NavLink>
                    </>
                )}
            </Navbar>
            <Outlet/>
        </>
    );
};

export default AppNavbar;
