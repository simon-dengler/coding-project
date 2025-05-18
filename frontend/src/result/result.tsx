import Container from '@mui/material/Container';
import {useParams} from "react-router-dom";
import baseApi from "../api/baseApi";
import {useEffect, useState} from "react";

interface ResultDto {
    name: string;
    formId: string;
}

function Result() {
    const {formId} = useParams();
    const [resultData, setResultData] = useState<ResultDto | null>(null);
    const formIdNumber = parseInt(formId ?? "0", 10);

    useEffect(() => {
        baseApi.getData("result/" + formIdNumber)
        .then(responseData => setResultData(responseData as ResultDto));
    }, [formIdNumber]);
    return (
        <Container maxWidth="sm">
            {resultData?.name}
        </Container>
    );
}

export default Result;