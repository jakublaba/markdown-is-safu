import React from "react";
import {useDispatch, useSelector} from "react-redux";
import {IAction, IAuthState} from "../types/types";
import {NavLink, Outlet, useNavigate} from "react-router-dom";
import {Button, Navbar} from "react-bootstrap";
import {Dispatch} from "redux";

const AppNavbar: React.FC = () => {
    const authenticated = useSelector<IAuthState, boolean>(authState => authState.authenticated);
    const dispatch = useDispatch<Dispatch<IAction>>();
    const navigate = useNavigate();

    const logout = () => {
        dispatch({
            type: "logout"
        });
        navigate("/login");
    };

    return (
        <>
            <Navbar>
                {authenticated ? (
                    <>
                        <NavLink to={"/"}>Profile</NavLink>
                        <NavLink to={"/notes"}>Notes</NavLink>
                        <Button onClick={logout}>Logout</Button>
                    </>
                ) : (
                    <>
                        <NavLink to={"/"}>Register</NavLink>
                        <NavLink to={"/login"}>Login</NavLink>
                    </>
                )}
            </Navbar>
            <Outlet/>
        </>
    );
};

export default AppNavbar;
