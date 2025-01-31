package Quiz.src.main.java.servlets;

import Quiz.src.main.java.models.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.annotation.WebServlet;

class HTMLTag {
    public String tag;
    public String _class;
    public String id;
    public String name;
    public ArrayList<HTMLTag> contents;
    public boolean closesInstantly;
    public String beforeStart;
    public String inner;
    public String action;
    public String method;
    public String src;
    public String type;
    public String value;
    public String _for;
    public String placeholder;
    public String rows;
    public String cols;
    public String selected;
    public String checked;

    public HTMLTag(String tag) {
        this.tag = tag;
        contents = new ArrayList<HTMLTag>();
    }

    public void add(HTMLTag htmlTag) {
        contents.add(htmlTag);
    }

    public String toString(String tabs) {
        String args = "";
        if (_class != null) {
            args += " class=\"" + _class + "\"";
        }
        if (id != null) {
            args += " id=\"" + id + "\"";
        }
        if (name != null) {
            args += " name=\"" + name + "\"";
        }
        if (action != null) {
            args += " action=\"" + action + "\"";
        }
        if (method != null) {
            args += " method=\"" + method + "\"";
        }
        if (src != null) {
            args += " src=\"" + src + "\"";
        }
        if (type != null) {
            args += " type=\"" + type + "\"";
        }
        if (value != null) {
            args += " value=\"" + value + "\"";
        }
        if (_for != null) {
            args += " for=\"" + _for + "\"";
        }
        if (placeholder != null) {
            args += " placeholder=\"" + placeholder + "\"";
        }
        if (rows != null) {
            args += " rows=\"" + rows + "\"";
        }
        if (cols != null) {
            args += " cols=\"" + cols + "\"";
        }
        if (selected != null) {
            args += " selected=\"" + selected + "\"";
        }
        if (checked != null) {
            args += " checked=\"" + checked + "\"";
        }
        if (closesInstantly) {
            args += " /";
        }
        String str_out = tabs;
        if (beforeStart != null) {
            str_out += beforeStart;
        }
        str_out += "<" + tag + args + ">";
        if (closesInstantly) {
            return str_out;
        }
        if (inner != null) {
            str_out += inner;
        }
        for (HTMLTag htmlTag : contents) {
            str_out += "\n" + htmlTag.toString(tabs + "  ");
        }
        String newLine = contents.size() > 0 ? "\n" + tabs : "";
        str_out += newLine + "</" + tag + ">";
        return str_out;
    }
}

class HTMLCodeBlocks{
    public static final String css = "" +
            "" +
            ".back-button {\n" +
            "    display: inline-block;\n" +
            "    background-color: #007bff;\n" +
            "    color: white;\n" +
            "    padding: 30px 60px\n" +
            "    text-decoration: none;\n" +
            "    font-size: 18px;\n" +
            " padding: 5px 10px;\n" +
            "    font-weight: bold;\n" +
            "    border-radius: 5px;\n" +
            "    margin-top: 10px;\n" +
            "}\n" +
            "\n" +
            ".back-button:hover {\n" +
            "    background-color: #0056b3;\n" +
            "}" +
            ".chemidivi{\n" +
            "display = flex;align-items: center;\n" +
            "  }\n" +
            ".block-title { \n" +
            "flex: 1; \n" +
            "margin-right: 10px; \n" +
            "}\n" +
            ".block-container {\n" +
            "    display: flex;\n" +
            "    /* align-items: center; */\n" +
            "    justify-content: center;\n" +
            "    margin-top: 10px;\n" +
            "    padding: 20px;\n" +
            "    border: 2px solid #007bff;\n" +
            "    border-radius: 12px;\n" +
            "}\n" +
            "\n" +
            ".block-contents {\n" +
            "    display: flex;\n" +
            "    flex-direction: column;\n" +
            "    width: 100%;\n" +
            "}\n" +
            "\n" +
            ".block-title {\n" +
            "    font-size: 40px;\n" +
            "}\n" +
            "\n" +
            ".block-items {\n" +
            "    font-size: 25px;\n" +
            "}\n" +
            "\n" +
            ".answer-list {\n" +
            "    font-size: 25px;\n" +
            "}\n" +
            "\n" +
            ".left-side {\n" +
            "    display: flex;\n" +
            "    flex-direction: column;\n" +
            "    align-items: center;\n" +
            "    padding-right: 20px;\n" +
            "}\n" +
            "\n" +
            ".right-side {\n" +
            "    flex: 1;\n" +
            "    display: flex;\n" +
            "    flex-direction: column;\n" +
            "    align-items: flex-start;\n" +
            "}\n" +
            "\n" +
            ".action-button {\n" +
            "    background-color: #2b91fe;\n" +
            "    color: white;\n" +
            "    border: 1px solid #000000;\n" +
            "    border-radius: 12px;\n" +
            "    padding: 5px 10px;\n" +
            "    cursor: pointer;\n" +
            "    border-radius: 5px;\n" +
            "    margin: 3px 3px 0 0;\n" +
            "}\n" +
            "\n" +
            ".note_text {\n" +
            "    margin-top: 10px;\n" +
            "    margin-bottom: 0px;\n" +
            "    resize: none;\n" +
            "    font-size: 18px;\n" +
            "}\n" +
            "\n" +
            ".question-form {\n" +
            "    font-size: 18px;\n" +
            "    list-style-type: none;\n" +
            "}\n" +
            "\n" +
            "select.question-form {\n" +
            "    font-size: 18px;\n" +
            "}\n" +
            "\n" +
            ".hidden {\n" +
            "    display: none;\n" +
            "}\n" +
            "\n" +
            ".sampleQuiz {\n" +
            "    width: min(100%, 600px);\n" +
            "}" +
            ".question-form {\n" +
            "    font-size: 18px;\n" +
            "    list-style-type: none;\n" +
            "}";
    public static final String scriptStart =
            "let questionsDeleted = 0;\n" +
            "const questionAnswerCounts = new Map();\n" +
            "let quizTagCount = 0;\n" +
            "\n" +
            "document.addEventListener(\"DOMContentLoaded\", function () {\n" +
            "\n" +
            "    document.getElementById(\"addQuestion\").addEventListener(\"click\", function (event) {\n" +
            "        event.preventDefault();\n" +
            "\n" +
            "        globalDivCount++;\n" +
            "        var divNum = globalDivCount - questionsDeleted;\n" +
            "        questionAnswerCounts.set(divNum, 0);\n" +
            "\n" +
            "        var newQuestion = document.createElement(\"div\");\n" +
            "        newQuestion.className = \"block-container\";\n" +
            "        newQuestion.id = `questionBlock${divNum}`;\n" +
            "\n" +
            "        addQuestionDiv(divNum, newQuestion);\n" +
            "\n" +
            "        var container = document.getElementById(\"quiz-contents\");\n" +
            "\n" +
            "        var addButton = document.getElementById(\"addQuestion\");\n" +
            "\n" +
            "        container.insertBefore(newQuestion, addButton);\n" +
            "\n" +
            "        document.getElementById(\"maxQuestionIndex\").value = globalDivCount;\n" +
            "\n" +
            "        addSelectEventListener(divNum);\n" +
            "\n" +
            "        addAddAnswerEventListener(divNum);\n" +
            "\n" +
            "        addDeleteQuestionListener(divNum);\n" +
            "    });\n" +
            "\n" +
            "    document.getElementById(\"addQuizTag\").addEventListener(\"click\", function (event) {\n" +
            "        event.preventDefault();\n" +
            "\n" +
            "        let quizTags = document.getElementById(\"quizTags\");\n" +
            "        let tagName = document.getElementById(\"quiz_new_tag\").value;\n" +
            "        if (tagName == \"\") {\n" +
            "            return;\n" +
            "        }\n" +
            "\n" +
            "        quizTagCount++;\n" +
            "\n" +
            "        let newTag = document.createElement(\"li\");\n" +
            "        newTag.id = `quiz_tag_item${quizTagCount}`;\n" +
            "\n" +
            "        let newTagName = document.createElement(\"span\");\n" +
            "        newTagName.innerHTML = tagName;\n" +
            "\n" +
            "        document.getElementById(\"quiz_tag_max_index\").value = quizTagCount;\n" +
            "\n" +
            "        let removeButton = document.createElement(\"button\");\n" +
            "        removeButton.classList.add(\"action-button\");\n" +
            "        removeButton.id = `removeTag${quizTagCount}`;\n" +
            "        removeButton.innerHTML = \"x\";\n" +
            "\n" +
            "        let tagNameInput = document.createElement(\"input\");\n" +
            "        tagNameInput.type = \"hidden\";\n" +
            "        tagNameInput.value = tagName;\n" +
            "        tagNameInput.name = `quiz_tag${quizTagCount}`;\n" +
            "\n" +
            "        newTag.appendChild(removeButton);\n" +
            "        newTag.appendChild(newTagName);\n" +
            "        newTag.appendChild(tagNameInput);\n" +
            "\n" +
            "        quizTags.appendChild(newTag);\n" +
            "        document.getElementById(\"quiz_new_tag\").value = \"\";\n" +
            "\n" +
            "        addRemoveTagListener(quizTagCount);\n" +
            "    });\n\n";
    public static final String scriptEnd = "});\n" +
            "function addRemoveTagListener(tagNum) {\n" +
            "    document.getElementById(`removeTag${tagNum}`).addEventListener(\"click\", function (event) {\n" +
            "        event.preventDefault();\n" +
            "\n" +
            "        let curTagNum = parseInt(this.id.replace(\"removeTag\", \"\"));\n" +
            "\n" +
            "        document.getElementById(`quiz_tag_item${curTagNum}`).remove();\n" +
            "    })\n" +
            "}\n" +
            "\n" +
            "function addAnswerBox(selectedVal, divNum, answerNum, addAnswerButton) {\n" +
            "    let newAnswer = document.createElement(\"div\");\n" +
            "    newAnswer.className = \"block-items\";\n" +
            "\n" +
            "    if (selectedVal == 0 || selectedVal == 3) {\n" +
            "        newAnswer.innerHTML = `\n" +
            "            <textarea name=\"q${divNum}\" class=\"note_text\" placeholder=\"Answer\" type=\"text\" rows=\"1\" cols=\"50\"></textarea>\n" +
            "        `;\n" +
            "        if (selectedVal == 3) {\n" +
            "            let questionTextCont = document.getElementById(`question-text${divNum}`);\n" +
            "            questionTextCont.placeholder = \"Image URL\";\n" +
            "        }\n" +
            "        addAnswerButton.classList.add(\"hidden\");\n" +
            "    } else if (selectedVal == 2) {\n" +
            "        let rbParams = \"\";\n" +
            "        if (answerNum == 1) {\n" +
            "            rbParams = \" checked=\\\"checked\\\"\";\n" +
            "        }\n" +
            "        newAnswer.innerHTML = `\n" +
            "            <input type=\"radio\" name=\"rb${divNum}\" value=${answerNum} ${rbParams}>\n" +
            "            <input name=\"q${divNum}-ans${answerNum}\" class=\"note_text\" placeholder=\"Answer ${answerNum}\" type=\"text\"></input>\n" +
            "        `;\n" +
            "    } else if (selectedVal == 5) {\n" +
            "        newAnswer.innerHTML = `\n" +
            "            <input type=\"checkbox\" name=\"cb${divNum}-ans${answerNum}\">\n" +
            "            <input name=\"q${divNum}-ans${answerNum}\" class=\"note_text\" placeholder=\"Answer ${answerNum}\" type=\"text\"></input>\n" +
            "        `;\n" +
            "    } else if (selectedVal == 4) {\n" +
            "        newAnswer.innerHTML = `\n" +
            "            <input name=\"q${divNum}-ans${answerNum}\" class=\"note_text\" placeholder=\"Answer ${answerNum}\" type=\"text\"></input>\n" +
            "        `;\n" +
            "    } else if (selectedVal == 1) {\n" +
            "        newAnswer.innerHTML = `\n" +
            "            ${answerNum}:\n" +
            "            <input name=\"q${divNum}-ans${answerNum}\" class=\"note_text\" placeholder=\"Blank ${answerNum}\" type=\"text\"></input>\n" +
            "        `;\n" +
            "    }\n" +
            "    let answerCountInput = document.getElementById(`answerCount${divNum}`);\n" +
            "    answerCountInput.value = answerNum;\n" +
            "\n" +
            "    let ansContainer = document.getElementById(`answerList${divNum}`);\n" +
            "    ansContainer.appendChild(newAnswer);\n" +
            "}\n" +
            "\n" +
            "function addQuestionDiv(divNum, newQuestion) {\n" +
            "    newQuestion.innerHTML = `\n" +
            "        <div class=\"block-contents\">\n" +
            "            <div class=\"block-items\"> \n" +
            "                <span id=\"question${divNum}\"> Question ${divNum} </span>\n" +
            "                <button class=\"action-button\" id=\"deleteQuestion${divNum}\">Delete</button>\n" +
            "            </div>\n" +
            "            <br>\n" +
            "            <input type=\"hidden\" id=\"answerCount${divNum}\" name=\"answerCount${divNum}\" value=\"0\">\n" +
            "            <select name=\"select${divNum}\" id=\"select${divNum}\" class=\"question-form\">\n" +
            "                <option value=\"\" disabled selected>Select Question Type</option>\n" +
            "                <option value=\"0\">Question Response</option>\n" +
            "                <option value=\"1\">Fill in the Blank</option>\n" +
            "                <option value=\"2\">Multiple Choice</option>\n" +
            "                <option value=\"3\">Picture Response</option>\n" +
            "                <option value=\"4\">Multiple Answer</option>\n" +
            "                <option value=\"5\">Multiple Choice & Answer</option>\n" +
            "            </select>\n" +
            "            <textarea id=\"question-text${divNum}\" name=\"question${divNum}\" class=\"note_text\" placeholder=\"Question\" rows=\"4\" cols=\"50\"></textarea>\n" +
            "            <div id=\"answerList${divNum}\" class=\"answer-list\">\n" +
            "            </div>\n" +
            "            <div id=\"answers${divNum}\" class=\"block-items\">\n" +
            "                <button class=\"action-button hidden\" id=\"addAnswer${divNum}\">Add Answer</button>\n" +
            "            </div>\n" +
            "        </div>\n" +
            "    `;\n" +
            "}\n" +
            "\n" +
            "function addSelectEventListener(divNum) {\n" +
            "    document.getElementById(`select${divNum}`).addEventListener(\"change\", function () {\n" +
            "        let curDivNum = parseInt(this.id.replace(\"select\", \"\"));\n" +
            "\n" +
            "        let addAnswerButton = document.getElementById(`addAnswer${divNum}`);\n" +
            "        addAnswerButton.classList.remove(\"hidden\");\n" +
            "        let selectedVal = this.value;\n" +
            "        let ansContainer = document.getElementById(`answerList${divNum}`);\n" +
            "        ansContainer.innerHTML = \"\";\n" +
            "\n" +
            "        questionAnswerCounts.set(curDivNum, 1);\n" +
            "        addAnswerBox(selectedVal, divNum, questionAnswerCounts.get(curDivNum), addAnswerButton);\n" +
            "    });\n" +
            "}\n" +
            "\n" +
            "function addAddAnswerEventListener(divNum) {\n" +
            "    document.getElementById(`addAnswer${divNum}`).addEventListener(\"click\", function (event) {\n" +
            "        event.preventDefault();\n" +
            "\n" +
            "        let curDivNum = parseInt(this.id.replace(\"addAnswer\", \"\"));\n" +
            "\n" +
            "        questionAnswerCounts.set(curDivNum, questionAnswerCounts.get(curDivNum) + 1);\n" +
            "        let selectedVal = document.getElementById(`select${curDivNum}`).value;\n" +
            "\n" +
            "        addAnswerBox(selectedVal, curDivNum, questionAnswerCounts.get(curDivNum), null);\n" +
            "    });\n" +
            "}\n" +
            "\n" +
            "function addDeleteQuestionListener(divNum) {\n" +
            "    document.getElementById(`deleteQuestion${divNum}`).addEventListener(\"click\", function (event) {\n" +
            "        event.preventDefault();\n" +
            "\n" +
            "        let curDivNum = parseInt(this.id.replace(\"deleteQuestion\", \"\"));\n" +
            "\n" +
            "        let questionBlock = document.getElementById(`questionBlock${curDivNum}`);\n" +
            "        questionBlock.remove();\n" +
            "\n" +
            "        for (let i = curDivNum + 1; i <= globalDivCount; i++) {\n" +
            "            let nextQuestionBody = document.getElementById(`question${i}`);\n" +
            "            if (nextQuestionBody === null) {\n" +
            "                continue;\n" +
            "            }\n" +
            "            nextQuestionBody.innerHTML = `Question ${i - 1}`;\n" +
            "        }\n" +
            "        questionsDeleted++;\n" +
            "    });\n" +
            "}";;
}

@WebServlet("/EditQuiz")
public class EditQuiz extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Edit Quiz Servlet");
        response.setContentType("text/html");
//        String referringURL = request.getHeader("referer");
        String referringURL = "./home.jsp";

        String script = "";

        try {

            String userId_req = request.getParameter("userId");
            String quizId_req = request.getParameter("quizId");

            int userId = Integer.parseInt(userId_req);
            int quizId = Integer.parseInt(quizId_req);

            DBConn dbConn = new DBConn();

            Quiz quiz  = dbConn.getQuiz(quizId);

            ArrayList<Question> questions = dbConn.getQuestions(quiz.id);

            ArrayList<ArrayList<Answer>> allAnswers = new ArrayList<>();

            for(Question question : questions) {
                ArrayList<Answer> answers = dbConn.getAnswers(question.id,false);
                allAnswers.add(answers);
            }
            ArrayList<Categorya> categoryas = dbConn.getCategories();
            int categoryId = dbConn.getQuizCategory(quiz.id);

            ArrayList<String> quizTags = dbConn.getQuizTags(quiz.id);

            dbConn.closeDBConn();

            script +=   "let globalDivCount = " + questions.size() + ";\n" + HTMLCodeBlocks.scriptStart;

            HTMLTag brTag = new HTMLTag("br");
            brTag.closesInstantly = true;

            HTMLTag html = new HTMLTag("html");
            html.beforeStart = "<!DOCTYPE html>\n";

            HTMLTag head = new HTMLTag("head");

            HTMLTag title = new HTMLTag("title");
            title.inner = "Edit Quiz";


            head.add(title);

            HTMLTag cssStyle = new HTMLTag("style");
            cssStyle.inner = HTMLCodeBlocks.css;

            head.add(cssStyle);
            html.add(head);

            HTMLTag body = new HTMLTag("body");


            HTMLTag quiz_summary = new HTMLTag("div");
            quiz_summary._class = "block-container";


            HTMLTag quiz_summary_cont = new HTMLTag("div");
            quiz_summary_cont._class = "block-contents";

            HTMLTag chemiaxalidivi = new HTMLTag("div");
            chemiaxalidivi._class = "chemidivi";

            HTMLTag quiz_summary_cont_title = new HTMLTag("div");
            quiz_summary_cont_title._class = "block-title";
            quiz_summary_cont_title.inner = "Edit Quiz";
            chemiaxalidivi.add(quiz_summary_cont_title);

            HTMLTag profileDiv = new HTMLTag("div");
            profileDiv.inner = "<a class=back-button href=/userProfile.jsp?id=" + userId + ">Profile</a>";
            chemiaxalidivi.add(profileDiv);

            quiz_summary_cont.add(chemiaxalidivi);


            HTMLTag form = new HTMLTag("form");
            form.action = "/CreateQuiz";
            form.method = "post";
            form.id = "quiz-contents";

            HTMLTag quiz_id_tag = new HTMLTag("input");
            quiz_id_tag.type = "hidden";
            quiz_id_tag.name = "quiz_id";
            quiz_id_tag.value = "" + quizId;

            form.add(quiz_id_tag);

            HTMLTag user_id_tag = new HTMLTag("input");
            user_id_tag.type = "hidden";
            user_id_tag.name = "userId";
            user_id_tag.value = "" + userId;

            form.add(user_id_tag);

            HTMLTag quiz_name = new HTMLTag("input");
            quiz_name.name = "quiz_name";
            quiz_name._class = "note_text";
            quiz_name.type = "text";
            quiz_name.value = quiz.quiz_name;
            quiz_name.closesInstantly = true;

            form.add(quiz_name);
            HTMLTag selectCategory = new HTMLTag("select");
            selectCategory.name = "quiz_category";
            selectCategory.id = "quiz_category";
            selectCategory._class = "question-form";

            HTMLTag noCatOption = new HTMLTag("option");
            noCatOption.value = "0";
            if(categoryId == 0){
                noCatOption.selected = "selected";
            }
            noCatOption.inner = "No Category";
            selectCategory.add(noCatOption);

            for(Categorya cat : categoryas){
                HTMLTag catOption = new HTMLTag("option");
                catOption.value = "" + cat.id;
                if(categoryId == cat.id){
                    catOption.selected = "selected";
                }
                catOption.inner = cat.category;
                selectCategory.add(catOption);
            }

            form.add(selectCategory);
            form.add(brTag);

            HTMLTag description = new HTMLTag("textarea");
            description.name = "description";
            description._class = "note_text";
            description.rows = "4";
            description.cols = "50";
            description.inner = quiz.description;

            form.add(description);
            form.add(brTag);

            HTMLTag quizTags_tag = new HTMLTag("ul");
            quizTags_tag.id = "quizTags";
            quizTags_tag._class = "question-form";

            int quiz_tag_id = 0;
            for(String quiz_tag : quizTags){
                quiz_tag_id++;
                HTMLTag quiz_tag_tag = new HTMLTag("li");
                quiz_tag_tag.id = "quiz_tag_item" + quiz_tag_id;

                HTMLTag delete_tag_button = new HTMLTag("button");
                delete_tag_button._class = "action-button";
                delete_tag_button.id = "removeTag" + quiz_tag_id;
                delete_tag_button.inner = "x";

                quiz_tag_tag.add(delete_tag_button);

                HTMLTag tag_name_tag = new HTMLTag("span");
                tag_name_tag.inner = quiz_tag;

                quiz_tag_tag.add(tag_name_tag);

                HTMLTag tag_input = new HTMLTag("input");
                tag_input.type = "hidden";
                tag_input.value = quiz_tag;
                tag_input.name = "quiz_tag" + quiz_tag_id;

                quiz_tag_tag.add(tag_input);

                quizTags_tag.add(quiz_tag_tag);

                script += "addRemoveTagListener(" + quiz_tag_id + ");\n";
            }

            form.add(quizTags_tag);

            HTMLTag add_quiz_tag = new HTMLTag("input");
            add_quiz_tag.id = "quiz_new_tag";
            add_quiz_tag._class = "note_text";
            add_quiz_tag.placeholder = "Tag";
            add_quiz_tag.type = "text";

            form.add(add_quiz_tag);

            HTMLTag add_quiz_tag_button = new HTMLTag("button");
            add_quiz_tag_button._class = "action-button";
            add_quiz_tag_button.id = "addQuizTag";
            add_quiz_tag_button.inner = "Add Tag";

            form.add(add_quiz_tag_button);

            HTMLTag quiz_tag_max_count = new HTMLTag("input");
            quiz_tag_max_count.name = "quiz_tag_max_index";
            quiz_tag_max_count.id = "quiz_tag_max_index";
            quiz_tag_max_count.type = "hidden";
            quiz_tag_max_count.value = "" + quizTags.size();

            form.add(quiz_tag_max_count);
            form.add(brTag);

            HTMLTag single_page_cb = new HTMLTag("input");
            single_page_cb.name = "isSinglePageCB";
            single_page_cb.type = "checkbox";

            if(quiz.is_single_page){
                single_page_cb.checked = "checked";
            }
            single_page_cb.closesInstantly = true;

            HTMLTag single_page_cb_label = new HTMLTag("label");
            single_page_cb_label._for = "isSinglePageCB";
            single_page_cb_label.inner = "Is single Page";

            form.add(single_page_cb);
            form.add(single_page_cb_label);
            form.add(brTag);

            HTMLTag can_be_practiced_cb = new HTMLTag("input");
            can_be_practiced_cb.name = "canBePracticedCB";
            can_be_practiced_cb.type = "checkbox";
            if(quiz.can_be_practiced){
                can_be_practiced_cb.checked = "checked";
            }
            can_be_practiced_cb.closesInstantly = true;

            HTMLTag can_be_practiced_cb_label = new HTMLTag("label");
            can_be_practiced_cb_label._for = "canBePracticedCB";
            can_be_practiced_cb_label.inner = "Can be practiced";

            form.add(can_be_practiced_cb);
            form.add(can_be_practiced_cb_label);
            form.add(brTag);

            HTMLTag rand_question_order_cb = new HTMLTag("input");
            rand_question_order_cb.name = "randQuestOrderCB";
            rand_question_order_cb.type = "checkbox";
            if(quiz.rand_question_order){
                rand_question_order_cb.checked = "checked";
            }
            rand_question_order_cb.closesInstantly = true;

            HTMLTag rand_question_order_cb_label = new HTMLTag("label");
            rand_question_order_cb_label._for = "randQuestOrderCB";
            rand_question_order_cb_label.inner = "Randomize question order";

            form.add(rand_question_order_cb);
            form.add(rand_question_order_cb_label);
            form.add(brTag);

            int questionNum = 0;
            for(Question question : questions){
                ArrayList<Answer> answers = allAnswers.get(questionNum);
                questionNum ++;
                script += "questionAnswerCounts.set(" + questionNum + ", " + answers.size() + ");";
                script += "        addSelectEventListener(" + questionNum + ");\n" +
                        "        addAddAnswerEventListener(" + questionNum + ");\n" +
                        "        addDeleteQuestionListener(" + questionNum + ");\n";
                HTMLTag question_block = new HTMLTag("div");
                question_block._class = "block-container";
                question_block.id = "questionBlock" + questionNum;
                HTMLTag question_content = new HTMLTag("div");
                question_content._class = "block-contents";

                HTMLTag question_items = new HTMLTag("div");
                question_items._class = "block-items";
                HTMLTag question_info = new HTMLTag("span");
                question_info.id = "question" + questionNum;
                question_info.inner = "Question " + questionNum;
                HTMLTag question_delete = new HTMLTag("button");
                question_delete.id = "deleteQuestion" + questionNum;
                question_delete._class = "action-button";
                question_delete.inner = "Delete";
                question_items.add(question_info);
                question_items.add(question_delete);

                question_content.add(question_items);
                question_content.add(brTag);

                HTMLTag answer_count = new HTMLTag("input");
                answer_count.closesInstantly = true;
                answer_count.type = "hidden";
                answer_count.id = "answerCount" + questionNum;
                answer_count.name = "answerCount" + questionNum;
                answer_count.value = "" + answers.size();

                question_content.add(answer_count);

                HTMLTag select = new HTMLTag("select");
                select.id = "select" + questionNum;
                select.name = "select" + questionNum;
                select._class = "question-form";
                ArrayList<String> questionTypes = new ArrayList<String>();
                questionTypes.add("Question Response");
                questionTypes.add("Fill in the Blank");
                questionTypes.add("Multiple Choice");
                questionTypes.add("Picture Response");
                questionTypes.add("Multiple Answer");
                questionTypes.add("Multiple Choice & Answers");
                int qTypeNum = 0;
                int selected = question.type.value;
                for(String questionType : questionTypes){
                    HTMLTag option = new HTMLTag("option");
                    option.value = "" + qTypeNum;
                    option.inner = questionType;
                    if(qTypeNum == selected){
                        option.selected = "selected";
                    }
                    select.add(option);
                    qTypeNum++;
                }
                question_content.add(select);
                HTMLTag question_text = new HTMLTag("textarea");
                question_text.id = "question-text" + questionNum;
                question_text.name = "question" + questionNum;
                question_text._class = "note_text";
                question_text.inner = question.question;
                question_text.rows = "4";
                question_text.cols = "50";
                question_content.add(question_text);
                HTMLTag answerList = new HTMLTag("div");
                answerList.id = "answerList" + questionNum;
                answerList._class = "answerList";
                int ansN = 0;
                for(Answer answer : answers) {
                    ansN++;
                    HTMLTag answerItems = new HTMLTag("div");
                    answerItems._class = "block-items";
                    if (selected == 0 || selected == 3){
                        HTMLTag answer_text = new HTMLTag("textarea");
                        answer_text.name = "q" + questionNum;
                        answer_text._class = "note_text";
                        answer_text.type = "text";
                        answer_text.rows = "1";
                        answer_text.cols = "50";
                        answer_text.inner = answer.answer;
                        answerItems.add(answer_text);
                    } else {
                        if(selected == 1){
                            answerItems.inner = "" + ansN + ": ";
                        } else if(selected == 2){
                            HTMLTag radio = new HTMLTag("input");
                            radio.name = "rb" + questionNum;
                            radio.type = "radio";
                            radio.value = "" + ansN;
                            radio.closesInstantly = true;
                            if(answer.isCorrect){
                                radio.checked = "checked";
                            }
                            answerItems.add(radio);
                        } else if (selected == 5){
                            HTMLTag check_box = new HTMLTag("input");
                            check_box.name = "cb" + questionNum + "-ans" + ansN;
                            check_box.type = "checkbox";
                            //check_box.value = "" + ansN;
                            check_box.closesInstantly = true;
                            if(answer.isCorrect){
                                check_box.checked = "checked";
                            }
                            answerItems.add(check_box);
                        }
                        HTMLTag answer_text = new HTMLTag("input");
                        answer_text.name = "q" + questionNum + "-ans" + ansN;
                        answer_text._class = "note_text";
                        answer_text.type = "text";
                        answer_text.value = answer.answer;
                        answer_text.closesInstantly = true;
                        answerItems.add(answer_text);
                    }
                    answerList.add(answerItems);
                }
                question_content.add(answerList);
                HTMLTag add_answer_div = new HTMLTag("div");
                add_answer_div.id = "answers" + questionNum;
                add_answer_div._class = "block-items";
                HTMLTag add_answer = new HTMLTag("button");
                add_answer.id = "addAnswer" + questionNum;
                add_answer._class = "action-button";
                if(selected == 0 || selected == 3) {
                    add_answer._class += " hidden";
                }
                add_answer.inner = "Add Answer";
                add_answer_div.add(add_answer);
                question_content.add(add_answer_div);
                question_block.add(question_content);
                form.add(question_block);
            }
            HTMLTag add_question = new HTMLTag("button");
            add_question._class = "action-button";
            add_question.id = "addQuestion";
            add_question.inner = "Add Question";
            form.add(add_question);
            form.add(brTag);
            HTMLTag submit = new HTMLTag("input");
            submit._class = "action-button";
            submit.type = "submit";
            submit.closesInstantly = true;
            form.add(submit);

            HTMLTag max_question_index = new HTMLTag("input");
            max_question_index.name = "maxQuestionIndex";
            max_question_index.id = "maxQuestionIndex";
            max_question_index.type = "hidden";
            max_question_index.value = "" + (questions.size() > 0 ? questions.get(questions.size()-1).num: 1);
            max_question_index.closesInstantly = true;
            form.add(max_question_index);
            quiz_summary_cont.add(form);
            quiz_summary.add(quiz_summary_cont);
            body.add(quiz_summary);

            HTMLTag scriptTag = new HTMLTag("script");
            script += HTMLCodeBlocks.scriptEnd;
            scriptTag.inner = script;
            body.add(scriptTag);

            html.add(body);

            response.getWriter().write(html.toString(""));

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
//            response.sendRedirect(referringURL);
        }
    }
}
