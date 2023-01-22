import React, {useEffect, useState} from "react";
import {INote, NOTE_API_URL} from "../types/axios-types";
import axios from "axios";
import {useSelector} from "react-redux";
import {IAuthState} from "../types/redux-types";
import {Button, ListGroup, Spinner} from "react-bootstrap";
import {useNavigate} from "react-router-dom";

const NoteList: React.FC = () => {
    const jwt = useSelector<IAuthState, string>(authState => authState.jwt);
    const navigate = useNavigate();
    const [loaded, setLoaded] = useState<boolean>(false);
    const [noteList, setNoteList] = useState<INote[]>([]);

    useEffect(() => {
        const fetchNotes = async () => await axios.get<Array<INote>>(
            `${NOTE_API_URL}/all`,
            {
                headers: {
                    Authorization: `Bearer ${jwt}`,
                }
            }
        );

        fetchNotes()
            .then((r) => {
                setNoteList(r.data);
            })
            .catch(err => {
                console.log(err.toJSON());
            })
            .finally(() => {
                setLoaded(true);
            });

    }, [jwt]);

    return (
        <>
            {loaded ? (
                noteList.length > 0 ? (
                    <ListGroup>
                        {noteList.map(note => {
                            const click = () => navigate(`/notes/editor/${note.uuid}`);

                            return (
                                <ListGroup.Item key={note.uuid} onClick={click}>
                                    {note.name}
                                </ListGroup.Item>
                            )
                        })}
                    </ListGroup>
                ) : (
                    <div>
                        <img src={require("../img/no-notes.jpg")} alt={"No notes?"}/>
                    </div>
                )
            ) : (
                <Spinner as={"span"}/>
            )}
            <Button onClick={() => navigate("/notes/editor")}>
                New note
            </Button>
        </>
    );
};

export default NoteList;
