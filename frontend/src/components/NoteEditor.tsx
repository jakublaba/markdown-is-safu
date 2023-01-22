import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {useSelector} from "react-redux";
import {IAuthState} from "../types/redux-types";
import axios, {AxiosResponse} from "axios";
import {NOTE_API_URL} from "../types/axios-types";
import {Button, Form, Spinner} from "react-bootstrap";
import ReactMarkdown from "react-markdown";

const NoteEditor: React.FC = () => {
    const {uuid} = useParams();
    const jwt = useSelector<IAuthState, string>(authState => authState.jwt);
    const [name, setName] = useState<string>("");
    const [markdown, setMarkdown] = useState<string>("");
    const [loaded, setLoaded] = useState<boolean>(!uuid);
    const [processing, setProcessing] = useState<boolean>(false);
    const [uploadErr, setUploadErr] = useState<boolean>(false);

    useEffect(() => {
        if (uuid) {
            const downloadNote = async (): Promise<AxiosResponse<Blob>> => await axios.get<Blob>(
                `${NOTE_API_URL}?uuid=${uuid}`,
                {
                    headers: {
                        Authorization: `Bearer ${jwt}`
                    }
                }
            );

            downloadNote()
                .then(async (r) => {
                    const header = r.headers["content-disposition"];
                    if (header) {
                        const start = header.indexOf('"') + 1;
                        const end = header.lastIndexOf('"');
                        setName(header.substring(start, end));
                    }
                    const text = await new Response(r.data).text();
                    setMarkdown(text);
                })
                .catch((err) => {
                    console.log(err.toJSON());
                })
                .finally(() => {
                    setLoaded(true);
                });
        }
    }, [jwt, uuid]);

    const save = async () => {
        setProcessing(true);
        const data = new FormData();
        const blob: Blob = new Blob([markdown], {type: "plain/text"});
        data.append("note", blob, name);

        if (uuid) {
            await axios.put(
                `${NOTE_API_URL}?uuid=${uuid}`,
                data,
                {
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                        "Content-Type": "multipart/form-data"
                    }
                }
            )
                .then(() => {
                    setUploadErr(false);
                })
                .catch(() => {
                    setUploadErr(true);
                })
                .finally(() => {
                    setProcessing(false);
                });
        } else {
            await axios.post(
                NOTE_API_URL,
                data,
                {
                    headers: {
                        Authorization: `Bearer ${jwt}`,
                        "Content-Type": "multipart/form-data"
                    }
                }
            )
                .then(() => {
                    setUploadErr(false);
                })
                .catch(() => {
                    setUploadErr(true);
                })
                .finally(() => {
                    setProcessing(false);
                });
        }
    };

    const updateName = (e: React.ChangeEvent<any>) => {
        setName(e.target.value);
    }

    const updateMarkdown = (e: React.ChangeEvent<any>) => {
        setMarkdown(e.target.value);
    }

    return (
        <>
            {loaded ? (
                <>
                    <Form>
                        <Form.Group>
                            <Form.Control
                                placeholder={"Note name"}
                                defaultValue={name}
                                onChange={updateName}
                            />
                        </Form.Group>

                        <Form.Group>
                            <Form.Control
                                id={"markdown"}
                                as={"textarea"}
                                placeholder={"Markdown goes here"}
                                defaultValue={markdown}
                                rows={20}
                                onChange={updateMarkdown}
                            />
                        </Form.Group>
                    </Form>

                    <ReactMarkdown>
                        {markdown}
                    </ReactMarkdown>

                    <Button onClick={save} disabled={processing}>
                        {processing ? <Spinner as={"span"}/> : "Save"}
                    </Button>
                </>
            ) : (
                <Spinner as={"span"} variant={"grow"}/>
            )}
            {uploadErr && (
                <div className={"alert alert-danger"}>
                    Upload error
                </div>
            )}
        </>
    );
};

export default NoteEditor;
