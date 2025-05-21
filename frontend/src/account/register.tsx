import {Container, Box, Typography, TextField, Button} from "@mui/material";
import {ChangeEvent, FormEvent, useState} from "react";
import {useLocation, useNavigate, useParams} from "react-router-dom";
import baseApi from "../api/baseApi";

interface FormData {
    password: string,
    repeatPassword: string,
}

export default function Register() {
    const username = useParams().username as string;
    const navigate = useNavigate();
    const location = useLocation();
    const [formData, setFormData] = useState<Partial<FormData>>({});
    const [passwordErrorMessage, setPasswordErrorMessage] = useState("");
    const from = location.state?.from?.pathname || "/";

    const validatePassword = (password: string) => {
        if (password.length < 6) {
            return "At least 6 Characters.";
        }
        return "";
    };

    const handleTextChange = (event: ChangeEvent<HTMLInputElement>) => {
        const value = event.target.value;
        const name = event.target.name;
        if (name === "password") {
            setPasswordErrorMessage(validatePassword(value));
        }
        setFormData(prev => ({
            ...prev,
            [name]: value
        }));
    };

    const handleSubmitFormData = (event: FormEvent<HTMLElement>) => {
        event.preventDefault();
        if (formData.password === formData.repeatPassword
            && username
        ) {
            const dto = {username: username, password: formData.password};
            baseApi.postData("auth/register", dto)
                .then(body => {
                    const response = body as { token: string };
                    localStorage.setItem("jwt", response.token);
                    localStorage.setItem("username", username);
                    navigate(from, {replace: true});
                })
                .catch(() => {
                    alert("Something went wrong. Please try again.");
                });
        } else {
            setPasswordErrorMessage("Passwords do not match.");
        }
    };

    return (
        <Container maxWidth="sm">
            <Box sx={{my: 4}}>
                <Typography variant="h4" component="h1" gutterBottom>
                    Sign Up
                </Typography>
                <Typography>
                    Please choose your password, {username}.
                </Typography>
            </Box>
            <Box component="form"
                noValidate
                autoComplete="off"
                onSubmit={handleSubmitFormData}
                sx={{
                    '& > :not(style)': {m: 1, width: '25ch'}
                }}>
                <div>
                    <TextField label="Password"
                        name="password"
                        type="password"
                        value={formData.password ?? ""}
                        required
                        onChange={handleTextChange}
                        error={!!passwordErrorMessage}
                        helperText={passwordErrorMessage}
                    />
                </div>
                <div>
                    <TextField label="Repeat Password"
                        name="repeatPassword"
                        type="password"
                        value={formData.repeatPassword ?? ""}
                        required
                        onChange={handleTextChange}
                    />
                </div>
                <Box sx={{float: "right"}}>
                    <Button type="button" onClick={() => { navigate("/"); }} sx={{mr: 1}}>
                        Cancel
                    </Button>
                    <Button type="submit" variant="contained">
                        Submit
                    </Button>
                </Box>
            </Box>
        </Container>
    );
}
