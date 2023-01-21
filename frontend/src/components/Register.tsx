import React, {useState} from "react";

import * as Yup from "yup";
import {ErrorMessage, Field, Form, Formik} from "formik";
import {Button, Spinner} from "react-bootstrap";
import {useNavigate} from "react-router-dom";
import {IRegisterFormData} from "../types/formdata-types";
import axios from "axios";
import {AUTH_API_URL, IRegisterRequest} from "../types/axios-types";

const Register: React.FC = () => {
    const navigate = useNavigate();
    const [loading, setLoading] = useState<boolean>(false);
    const [registrationErr, setRegistrationErr] = useState<boolean>(false);
    const [passwordVisible, setPasswordVisible] = useState<boolean>(false);

    const initialValues: IRegisterFormData = {
        username: "",
        email: "",
        password: "",
        repeatPassword: ""
    };

    const validationSchema = Yup.object().shape({
        username: Yup.string()
            .required("Required field")
            .min(4, "Minimum 4 characters required")
            .max(32, "Maximum 32 characters allowed")
            .matches(
                /^[a-zA-Z0-9_-]+$/,
                "Special characters besides _ and - not allowed"
            ),
        email: Yup.string()
            .required("Required field")
            .min(4, "Minimum 4 characters required")
            .max(64, "Maximum 64 characters allowed")
            .email("This is not a valid email"),
        password: Yup.string()
            .required("Required field")
            .min(8, "Minimum 8 characters required")
            .max(128, "Maximum 128 characters allowed")
            .matches(
                /^(?=(.*[a-z])+)(?=(.*[A-Z])+)(?=(.*\d)+)(?=(.*[!@#$%^&*()\-_+.])).{4,}$/,
                "Must contain at least 1 of each: lowercase character, uppercase character, digit, special character"
            ),
        repeatPassword: Yup.string()
            .required("Required field")
            .test(
                "matches",
                "Passwords don't match",
                function (repeatPass: any) {
                    return this.parent.password === repeatPass;
                }
            )
    });

    const register = async (formData: IRegisterFormData) => {
        setLoading(true);
        const requestBody: IRegisterRequest = {
            username: formData.username,
            email: formData.email,
            password: formData.password
        };

        try {
            await axios.post(`${AUTH_API_URL}/register`, requestBody);
            navigate("/login");
        } catch (err) {
            setRegistrationErr(true);
            setLoading(false);
        }
    };

    const togglePassword = () => {
        setPasswordVisible(!passwordVisible);
    };

    return (
        <Formik
            initialValues={initialValues}
            validationSchema={validationSchema}
            onSubmit={register}
        >
            <Form>
                <Field
                    name={"username"}
                    type={"text"}
                    placeholder={"Username"}
                    className={"form-control"}
                />
                <ErrorMessage
                    name="username"
                    component="div"
                    className="alert alert-danger"
                />

                <Field
                    name={"email"}
                    type={"email"}
                    placeholder={"Email address"}
                    className={"form-control"}
                />
                <ErrorMessage
                    name="email"
                    component="div"
                    className="alert alert-danger"
                />

                <Field
                    name={"password"}
                    type={passwordVisible ? "text" : "password"}
                    placeholder={"Password"}
                    className={"form-control"}
                />
                <ErrorMessage
                    name="password"
                    component="div"
                    className="alert alert-danger"
                />

                <Field
                    name={"repeatPassword"}
                    type={passwordVisible ? "text" : "password"}
                    placeholder={"Repeat password"}
                    className={"form-control"}
                />
                <ErrorMessage
                    name="repeatPassword"
                    component="div"
                    className="alert alert-danger"
                />

                <Button type="submit" className="btn btn-primary btn-block" disabled={loading}>
                    {loading
                        ? <Spinner as={"span"}></Spinner>
                        : "Sign up"
                    }
                </Button>
                <Button onClick={togglePassword} disabled={loading}>
                    {passwordVisible ? "Hide password" : "Show password"}
                </Button>

                {registrationErr && (
                    <div className={"alert alert-danger"}>
                        Registration failed
                    </div>
                )}
            </Form>
        </Formik>
    );
};

export default Register;