import axios from "axios";

const baseURL = "http://localhost:4000/mocking/nabchallenge/v1";

export default axios.create({ baseURL });
