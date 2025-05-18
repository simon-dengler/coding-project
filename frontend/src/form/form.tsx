import {Button, TextField, Container, Typography, Box} from "@mui/material";
import {ChangeEvent, FormEvent, useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import baseApi from "../api/baseApi";

interface FormData {
    id: number | null;
    firstName: string;
    lastName: string;
    email: string;
    phone: string;
    favouriteAnimal: string;
    zodiac: string;
}

export interface ErrorData {
    error: string;
    status: number;
    message: string;
    trace?: string;
}

function Form() {
    const {formId} = useParams();
    const formIdNumber = formId ? parseInt(formId, 10) : null;
    const navigate = useNavigate();
    const [formData, setFormData] = useState<Partial<FormData>>({id: formIdNumber});

    const handleTextChange = (event: ChangeEvent<HTMLInputElement>) => {
        const value = event.target.value;
        const name = event.target.name;
        setFormData(prev => ({
            ...prev,
            [name]: value
        }));
    };

    useEffect(() => {
        if (formIdNumber) {
            baseApi.getData("form/" + formIdNumber)
                .then(responseData => setFormData(responseData as FormData));
        }
    }, [formIdNumber]);

    const handleSubmitFormData = (event: FormEvent<HTMLElement>) => {
        event.preventDefault();
        baseApi.postData("form", formData).then(id => {
            navigate("/form/" + id);
            navigate("/game/" + id);
        });
    };

    return (
        <Container maxWidth="sm">
            <Box sx={{my: 4}}>
                <Typography variant="h4" component="h1" gutterBottom>
                    Personal data
                </Typography>
                <Typography>
                    Enter your personal datas so we know how to reach you.  
                </Typography>
            </Box>
            <Box component="form"
                 noValidate
                 autoComplete="off"
                 onSubmit={handleSubmitFormData}
                 sx={{
                     '& > :not(style)': {m: 1, width: '25ch'},
                 }}
            >
                <TextField label="Firstname"
                           name="firstName"
                           value={formData.firstName ?? ""}
                           required
                           onChange={handleTextChange}
                />
                <TextField label="Lastname"
                           name="lastName"
                           value={formData.lastName ?? ""}
                           required
                           onChange={handleTextChange}
                />
                <TextField label="Email"
                           name="email"
                           value={formData.email ?? ""}
                           required
                           onChange={handleTextChange}
                />
                <TextField label="Phone"
                           name="phone"
                           value={formData.phone ?? ""}
                           onChange={handleTextChange}
                />
                <TextField label="Favourite Animal"
                           name="favouriteAnimal"
                           value={formData.favouriteAnimal ?? ""}
                           onChange={handleTextChange}
                />
                <TextField label="Zodiac Sign"
                           name="zodiac"
                           value={formData.zodiac ?? ""}
                           onChange={handleTextChange}
                />

                <Box sx={{float: "right"}}>
                    <Button type="reset" sx={{mr: 1}}>
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

export default Form;