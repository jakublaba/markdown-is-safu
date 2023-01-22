import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import {useSelector} from "react-redux";
import {IAuthState} from "../types/redux-types";
import axios, {AxiosResponse} from "axios";
import {NOTE_API_URL} from "../types/axios-types";
import {Button, Form, Spinner} from "react-bootstrap";
import ReactMarkdown from "react-markdown";

const NoteEditor: React.FC = () => {
    // if uuid is undefined, user got here by clicking new note button - create mode
    // otherwise, user got her by clicking note on the list - edit mode
    const {uuid} = useParams();
    const jwt = useSelector<IAuthState, string>(authState => authState.jwt);
    const [loaded, setLoaded] = useState<boolean>(!uuid); // in edit mode, file has to be loaded first
    const [processing, setProcessing] = useState<boolean>(false);
    const [uploadErr, setUploadErr] = useState<boolean>(false);
    // TODO - figure out how to retrieve file name from downloaded note
    const [name, setName] = useState<string>("");
    const [content, setContent] = useState<string>("");

    useEffect(() => {
        if (uuid) {
            const downloadNote = async (): Promise<AxiosResponse<Blob>> => await axios.get<Blob>(
                `${NOTE_API_URL}/${uuid}`,
                {
                    headers: {
                        Authorization: `Bearer ${jwt}`
                    }
                }
            );

            downloadNote()
                .then(async (r) => {
                    const text = await r.data.text();
                    setContent(text);
                })
                .catch((err) => {
                    console.log(err.toJSON());
                })
                .finally(() => {
                    setLoaded(true);
                });
        }
    }, []);

    const save = async () => {
        setProcessing(true);
        const data = new FormData();
        const blob: Blob = new Blob([content], {type: "plain/text"});
        data.append("note", blob, name);

        if (uuid) {
            // update note
            await axios.put(
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
        } else {
            // upload new note
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

    return (
        <>
            {loaded ? (
                <>
                    <Form>
                        <Form.Group>
                            <Form.Control
                                placeholder={"Note name"}
                                onChange={(e) => setName(e.target.value)}
                            />
                        </Form.Group>

                        <Form.Group>
                            <Form.Control
                                as={"textarea"}
                                placeholder={"Markdown goes here"}
                                onChange={(e) => setContent(e.target.value)}
                            />
                        </Form.Group>

                        <Button onClick={save} disabled={processing}>
                            {processing ? <Spinner as={"span"}/> : "Save"}
                        </Button>
                    </Form>
                    <ReactMarkdown>
                        {content}
                    </ReactMarkdown>
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
