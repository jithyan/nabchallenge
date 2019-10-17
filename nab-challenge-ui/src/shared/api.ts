import axios from 'axios';

const baseURL = "localhost:4000/mocking/";

export default axios.create({baseURL});