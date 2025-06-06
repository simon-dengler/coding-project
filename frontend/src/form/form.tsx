import {Button, TextField, Container, Typography, Box} from "@mui/material";
import {ChangeEvent, FormEvent, useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import baseApi from "../api/baseApi";
import {parsePhoneNumberFromString} from "libphonenumber-js";

interface FormData {
    id: number | null;
    firstName: string;
    lastName: string;
    email: string;
    phone: string;
    favouriteAnimal: string;
    zodiac: string;
}

interface User {
    id: number | null,
    username: string,
    formDataId: number | null,
}

export interface ErrorData {
    error: string;
    status?: number;
    message?: string;
    trace?: string;
}

function Form() {
    const {formId} = useParams();
    const [formIdNumber, setFormIdNumber] = useState(formId ? parseInt(formId, 10) : null);
    const navigate = useNavigate();
    const [formData, setFormData] = useState<Partial<FormData>>({id: formIdNumber});
    const [phoneErrorMessage, setPhoneErrorMessage] = useState("");

    const handleTextChange = (event: ChangeEvent<HTMLInputElement>) => {
        const value = event.target.value;
        const name = event.target.name;
        if(name === "phone"){
            setPhoneErrorMessage(validatePhoneNumber(value));
        }
        setFormData(prev => ({
            ...prev,
            [name]: value
        }));
    };

    useEffect(() => {
        if (formIdNumber) {
            baseApi.getData("form/" + formIdNumber)
                .then(responseData => setFormData(responseData as FormData))
                .catch(error => {
                    const errorData = error as ErrorData;
                    console.error(`${errorData.error}: Received HTTP ${errorData.status}: ${errorData.message}`);
                    navigate("/");
                });
        } else {
            baseApi.getData("account")
            .then(responesData => {
                const user = responesData as User;
                if(user.formDataId){
                    setFormIdNumber(user.formDataId);
                }
            })
            .catch(error => {
                const errorData = error as ErrorData;
                console.error(`${errorData.error}: Received HTTP ${errorData.status}: ${errorData.message}`);
            });
        }
    }, [formIdNumber]);

    const handleSubmitFormData = (event: FormEvent<HTMLElement>) => {
        event.preventDefault();
        baseApi.postData("form", formData).then(id => {
            navigate("/form/" + id); // ?? 
            navigate("/game/" + id);
        })
        .catch(errorData => {
            console.error(errorData.error);
        });
    };

    const validatePhoneNumber = (phoneInput: string) => {
        const phoneNumber = parsePhoneNumberFromString(phoneInput);
        if(phoneNumber?.isValid() && phoneNumber?.format('E.164') === phoneInput){
            return "";
        } else if (phoneInput.length > 15) {
            return "max. 15 digits";
        } else if (!phoneInput.startsWith("+")) {
            return "please start with '+COUNTRYCODE'";
        } else if(phoneInput.indexOf(" ") != -1) {
            return "please remove any spaces";
        }
        return "Please provide an E.164 compatible number.";
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
                           error={!!phoneErrorMessage}
                           helperText={phoneErrorMessage}
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
                    <Button type="button" onClick={()=>{navigate("/");}} sx={{mr: 1}}>
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