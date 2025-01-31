let globalDivCount = 0;
let questionsDeleted = 0;
const questionAnswerCounts = new Map();
let quizTagCount = 0;

document.addEventListener("DOMContentLoaded", function () {

    document.getElementById("addQuestion").addEventListener("click", function (event) {
        event.preventDefault();

        globalDivCount++;
        var divNum = globalDivCount - questionsDeleted;
        questionAnswerCounts.set(divNum, 0);

        var newQuestion = document.createElement("div");
        newQuestion.className = "block-container";
        newQuestion.id = `questionBlock${divNum}`;

        addQuestionDiv(divNum, newQuestion);

        var container = document.getElementById("quiz-contents");

        var addButton = document.getElementById("addQuestion");

        container.insertBefore(newQuestion, addButton);

        document.getElementById("maxQuestionIndex").value = globalDivCount;

        addSelectEventListener(divNum);

        addAddAnswerEventListener(divNum);

        addDeleteQuestionListener(divNum);
    });

    document.getElementById("addQuizTag").addEventListener("click", function (event) {
        event.preventDefault();

        let quizTags = document.getElementById("quizTags");
        let tagName = document.getElementById("quiz_new_tag").value;
        if (tagName == "") {
            return;
        }

        quizTagCount++;

        let newTag = document.createElement("li");
        newTag.id = `quiz_tag_item${quizTagCount}`;

        let newTagName = document.createElement("span");
        newTagName.innerHTML = tagName;

        document.getElementById("quiz_tag_max_index").value = quizTagCount;

        let removeButton = document.createElement("button");
        removeButton.classList.add("action-button");
        removeButton.id = `removeTag${quizTagCount}`;
        removeButton.innerHTML = "x";

        let tagNameInput = document.createElement("input");
        tagNameInput.type = "hidden";
        tagNameInput.value = tagName;
        tagNameInput.name = `quiz_tag${quizTagCount}`;

        newTag.appendChild(removeButton);
        newTag.appendChild(newTagName);
        newTag.appendChild(tagNameInput);

        quizTags.appendChild(newTag);
        document.getElementById("quiz_new_tag").value = "";

        addRemoveTagListener(quizTagCount);
    });
});

function addRemoveTagListener(tagNum) {
    document.getElementById(`removeTag${tagNum}`).addEventListener("click", function (event) {
        event.preventDefault();

        let curTagNum = parseInt(this.id.replace("removeTag", ""));

        document.getElementById(`quiz_tag_item${curTagNum}`).remove();
    })
}

function addAnswerBox(selectedVal, divNum, answerNum, addAnswerButton) {
    let newAnswer = document.createElement("div");
    newAnswer.className = "block-items";

    if (selectedVal == 0 || selectedVal == 3) {
        newAnswer.innerHTML = `
            <textarea name="q${divNum}" class="note_text" placeholder="Answer" type="text" rows="1" cols="50"></textarea>
        `;
        if (selectedVal == 3) {
            let questionTextCont = document.getElementById(`question-text${divNum}`);
            questionTextCont.placeholder = "Image URL";
        }
        addAnswerButton.classList.add("hidden");
    } else if (selectedVal == 2) {
        let rbParams = "";
        if (answerNum == 1) {
            rbParams = " checked=\"checked\"";
        }
        newAnswer.innerHTML = `
            <input type="radio" name="rb${divNum}" value=${answerNum} ${rbParams}>
            <input name="q${divNum}-ans${answerNum}" class="note_text" placeholder="Answer ${answerNum}" type="text"></input>
        `;
    } else if (selectedVal == 5) {
        newAnswer.innerHTML = `
            <input type="checkbox" name="cb${divNum}-ans${answerNum}">
            <input name="q${divNum}-ans${answerNum}" class="note_text" placeholder="Answer ${answerNum}" type="text"></input>
        `;
    } else if (selectedVal == 4) {
        newAnswer.innerHTML = `
            <input name="q${divNum}-ans${answerNum}" class="note_text" placeholder="Answer ${answerNum}" type="text"></input>
        `;
    } else if (selectedVal == 1) {
        newAnswer.innerHTML = `
            ${answerNum}:
            <input name="q${divNum}-ans${answerNum}" class="note_text" placeholder="Blank ${answerNum}" type="text"></input>
        `;
    }
    let answerCountInput = document.getElementById(`answerCount${divNum}`);
    answerCountInput.value = answerNum;

    let ansContainer = document.getElementById(`answerList${divNum}`);
    ansContainer.appendChild(newAnswer);
}

function addQuestionDiv(divNum, newQuestion) {
    newQuestion.innerHTML = `
        <div class="block-contents">
            <div class="block-items"> 
                <span id="question${divNum}"> Question ${divNum} </span>
                <button class="action-button" id="deleteQuestion${divNum}">Delete</button>
            </div>
            <br>
            <input type="hidden" id="answerCount${divNum}" name="answerCount${divNum}" value="0">
            <select name="select${divNum}" id="select${divNum}" class="question-form">
                <option value="" disabled selected>Select Question Type</option>
                <option value="0">Question Response</option>
                <option value="1">Fill in the Blank</option>
                <option value="2">Multiple Choice</option>
                <option value="3">Picture Response</option>
                <option value="4">Multiple Answer</option>
                <option value="5">Multiple Choice & Answer</option>
            </select>
            <textarea id="question-text${divNum}" name="question${divNum}" class="note_text" placeholder="Question" rows="4" cols="50"></textarea>
            <div id="answerList${divNum}" class="answer-list">
            </div>
            <div id="answers${divNum}" class="block-items">
                <button class="action-button hidden" id="addAnswer${divNum}">Add Answer</button>
            </div>
        </div>
    `;
}

function addSelectEventListener(divNum) {
    document.getElementById(`select${divNum}`).addEventListener("change", function () {
        let curDivNum = parseInt(this.id.replace("select", ""));

        let addAnswerButton = document.getElementById(`addAnswer${divNum}`);
        addAnswerButton.classList.remove("hidden");
        let selectedVal = this.value;
        let ansContainer = document.getElementById(`answerList${divNum}`);
        ansContainer.innerHTML = "";

        questionAnswerCounts.set(curDivNum, 1);
        addAnswerBox(selectedVal, divNum, questionAnswerCounts.get(curDivNum), addAnswerButton);
    });
}

function addAddAnswerEventListener(divNum) {
    document.getElementById(`addAnswer${divNum}`).addEventListener("click", function (event) {
        event.preventDefault();

        let curDivNum = parseInt(this.id.replace("addAnswer", ""));

        questionAnswerCounts.set(curDivNum, questionAnswerCounts.get(curDivNum) + 1);
        let selectedVal = document.getElementById(`select${curDivNum}`).value;

        addAnswerBox(selectedVal, curDivNum, questionAnswerCounts.get(curDivNum), null);
    });
}

function addDeleteQuestionListener(divNum) {
    document.getElementById(`deleteQuestion${divNum}`).addEventListener("click", function (event) {
        event.preventDefault();

        let curDivNum = parseInt(this.id.replace("deleteQuestion", ""));

        let questionBlock = document.getElementById(`questionBlock${curDivNum}`);
        questionBlock.remove();

        for (let i = curDivNum + 1; i <= globalDivCount; i++) {
            let nextQuestionBody = document.getElementById(`question${i}`);
            if (nextQuestionBody === null) {
                continue;
            }
            nextQuestionBody.innerHTML = `Question ${i - 1}`;
        }
        questionsDeleted++;
    });
}