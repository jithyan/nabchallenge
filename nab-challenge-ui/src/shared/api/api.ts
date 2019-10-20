import axios from "axios";
const mockingBaseURL = "http://localhost:4000/mocking/nabchallenge/v1";
const BFFURL = "http://localhost:50555/nab-challenge/v1/";

const baseURL = BFFURL;

export default axios.create({ baseURL });
