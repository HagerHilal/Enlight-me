const Joi = require('joi')

module.exports = {

    createValidation: request => {

        const createSchema = {

            email: Joi.string().email().required(),
            username: Joi.string().min(5).max(100).required(),
            name: Joi.string().required(),
            password: Joi.string().required().min(8).regex(/[!@#$%^&*()_+\-=\[\]{};':'\\|,.<>\/?]/),
            
        }



        return Joi.validate(request, createSchema)

    }
}