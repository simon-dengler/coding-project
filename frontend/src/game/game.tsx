import {Link as RouterLink, useNavigate, useParams} from "react-router-dom";
import {Container, Typography, Box, Button} from "@mui/material";
import {useEffect, useState} from "react";
import Result from "../result/result";
import baseApi from "../api/baseApi";

interface Question {
    category: string,
    question: string,
    correct_answer: string,
}

interface Result {
    category: string,
    question: string,
    correctAnswer: string,
    answeredCorrectly: boolean,
    formDataId: number
}

function Game() {
    const {formId} = useParams();
    const [allowedToContinue, setAllowedToContinue] = useState(false);
    const [question, setQuestion] = useState<Partial<Question>>({});
    const [gameStarted, setGameStarted] = useState(false);
    const [wrongAnswer, setWrongAnswer] = useState(false);
    const navigate = useNavigate();
    const [jackpotId, setJackpodId] = useState("");

    const resolve = (answer: string) => {
        const correct = answer === question.correct_answer;
        if(correct){
            setAllowedToContinue(true);
        } else {
            setWrongAnswer(true);
        }
        const result: Partial<Result> = {
                category: question.category,
                question: question.question,
                correctAnswer: question.correct_answer,
                answeredCorrectly: correct,
                formDataId: parseInt(formId ?? "0")
            };
        if(correct===true){
            baseApi.postData("result/"+formId, result)
            .then(id => {
                setJackpodId(String(id));
            })
            .catch(errorData => {
                console.error(errorData.error);
            });
        } else {
            baseApi.postData("result/"+formId, result)
            .catch(e => {
                console.error(e.error);
            });
        }
    };

    const myHeaders = new Headers();
    myHeaders.append("Accept", "application/json");

    const requestOptions = {
        method: "GET",
        headers: myHeaders,
        redirect: "follow" as RequestRedirect
    };

    const questionBox = {
        display: "flex",
        flexDirection: "column",
        padding: 2,
        marginBottom: 3,
        borderRadius: 2,
        backgroundColor: "#556cd680",
        gap: 2,
    };

    useEffect(() => {
        fetch("https://opentdb.com/api.php?amount=1&type=boolean", requestOptions)
        .then(res => res.json())
        .then(response => {
            const quiz = response.results[0] as Question;
            setQuestion(quiz);
        })
        .catch(error => {
            console.error(error);
            alert("Something went wrong. Please reload.");
        });
    }, []);

    return (
        <Container maxWidth="sm">
            <Box sx={{my: 4}}>
                <Typography variant="h4" component="h1" gutterBottom>
                    Game
                </Typography>
                <Typography>
                    If you can solve the quiz, you might earn a reward.   
                </Typography>
                {!gameStarted && (<Button type="button" 
                    onClick={() => setGameStarted(true)}
                    sx={{mt:1}}>
                    Start Game
                </Button>)}
            </Box>
            {gameStarted && (<Box sx={questionBox}>
                <Typography sx={{fontWeight: "bold"}}>
                    Category: {decodeHtml(question.category)}
                </Typography>
                <Typography>
                    {decodeHtml(question.question)}
                </Typography>
                <Box sx={{display: "flex", justifyContent:"space-evenly"}}>
                    <Button type="button" 
                        variant="contained"  
                        color="success" 
                        onClick={() => resolve("True")}>
                        True
                    </Button>
                    <Button type="button" 
                        variant="contained"
                        color="error"
                        onClick={() => resolve("False")}>
                        False
                    </Button>
                </Box>
            </Box>)}
            {
                allowedToContinue && (
                <Box>
                    <Box sx={{my:1}}>
                        <Typography>
                            That is the correct answer! You can now continue to the reward lottery. 
                        </Typography>
                    </Box>    
                    <Box sx={{float: "right"}}>
                        <Button type="button" onClick={() => navigate("/result/" + jackpotId)}>
                            Continue
                        </Button>
                    </Box>
                </Box>)
            }
            {
                wrongAnswer && (
                <Box>
                    <Box sx={{my:1}}>
                        <Typography>
                            That is not correct. You can try again.  
                        </Typography>
                    </Box>    
                    <Box>
                        <Button type="button" variant="contained" onClick={() => window.location.reload()}>
                            Try again
                        </Button>
                    </Box>
                </Box>)
            }
        </Container>
    );
}

function decodeHtml(rawText?: string): string {
  const text = document.createElement("textarea");
  if (rawText){
      text.innerHTML = rawText;
      return text.value;
  }
  return "";
}

export default Game;