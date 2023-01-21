import React, {useState} from "react";
import {useDispatch} from "react-redux";
import {IAction} from "../types/redux-types";
import {Dispatch} from "redux";
import {Button, Spinner} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import {ErrorMessage, Field, Form, Formik} from "formik";
import * as Yup from "yup";
import {ILoginFormData} from "../types/formdata-types";
import axios from "axios";
import {ILoginResponse} from "../types/axios-types";

const Login: React.FC = () => {
    const dispatch = useDispatch<Dispatch<IAction>>();
    const navigate = useNavigate();
    const [loading, setLoading] = useState<boolean>(false);
    const [showLoginErr, setShowLoginErr] = useState<boolean>(false);
    const [passwordVisible, setPasswordVisible] = useState<boolean>(false);

    const initialValues: ILoginFormData = {
        username: "",
        password: ""
    };

    const validationSchema = Yup.object().shape({
        username: Yup.string().required("Required field"),
        password: Yup.string().required("Required field")
    });

    const login = async (formData: ILoginFormData) => {
        setLoading(true);
        const {username, password} = formData;

        try {
            const response = await axios.post<ILoginResponse>(
                "http://localhost:8080/api/auth/login",
                {
                    username: username,
                    password: password
                }
            );
            dispatch({
                type: "login",
                jwt: response.data.jwt
            });
            navigate("/");
        } catch (err) {
            setLoading(false);
            setShowLoginErr(true);
        }
    };

    const togglePassword = () => {
        setPasswordVisible(!passwordVisible);
    }

    return (
        <Formik
            initialValues={initialValues}
            validationSchema={validationSchema}
            onSubmit={login}
        >
            <Form>
                <Field
                    name={"username"}
                    type={"text"}
                    placeholder={"Username"}
                    className={"form-control"}
                />
                <ErrorMessage
                    name={"username"}
                    component={"div"}
                    className={"alert alert-danger"}
                />

                <Field
                    name={"password"}
                    type={passwordVisible ? "text" : "password"}
                    placeholder={"Password"}
                    className="form-control"
                />
                <ErrorMessage
                    name={"password"}
                    component={"div"}
                    className={"alert alert-danger"}
                />

                <Button type={"submit"} disabled={loading}>
                    {loading
                        ? <Spinner as={"span"}/>
                        : "Login"
                    }
                </Button>

                <Button onClick={togglePassword} disabled={loading}>
                    {passwordVisible ? "Hide password" : "Show password"}
                </Button>

                {showLoginErr && (
                    <div className={"alert alert-danger"}>
                        Invalid username or password
                    </div>
                )}
            </Form>
        </Formik>
    );
};

export default Login;
