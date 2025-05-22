import Container from '@mui/material/Container';
import {useParams} from "react-router-dom";
import baseApi from "../api/baseApi";
import {useEffect, useState} from "react";
import {Box, Typography} from '@mui/material';

interface ResultDto {
    name: string;
    description: string;
}

function Result() {
    const {jackpotId} = useParams();
    const [resultData, setResultData] = useState<ResultDto | null>(null);
    const jackpotIdNumber = parseInt(jackpotId ?? "0", 10);

    useEffect(() => {
        baseApi.getData("result/" + jackpotIdNumber)
        .then(responseData => setResultData(responseData as ResultDto));
    }, [jackpotIdNumber]);
    return (
        <Container maxWidth="sm">
            <Box sx={{my: 4}}>
                <Typography variant="h4" component="h1" gutterBottom>
                    {resultData?.name}
                </Typography>
                <Typography>
                    {resultData?.description}   
                </Typography>
            </Box>
        </Container>
    );
}

export default Result;
