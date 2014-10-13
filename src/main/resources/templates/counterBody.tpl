input (type: 'button', value: 'Next!', onclick: "nextNumber(${model.number})")
div(id: 'numbers') {
    h1 "${model.number}"
}