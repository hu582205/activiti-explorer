package hu.itget.io.activiti;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.activiti.editor.constants.ModelDataJsonConstants;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Controller
@RequestMapping("/models")
public class TestActivitiController {

	private static final Logger LOGGER = LoggerFactory.getLogger(TestActivitiController.class);

	@Autowired
	private RepositoryService repositoryService;
	@Autowired
	private ObjectMapper objectMapper;

	@RequestMapping("/create")
	public void newModel(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		try {
			// 初始化一个空模型
			Model model = repositoryService.newModel();

			// 设置一些默认信息
			String name = "new-process";
			String description = "";
			int revision = 1;
			String key = "process";

			ObjectNode modelNode = objectMapper.createObjectNode();
			modelNode.put(ModelDataJsonConstants.MODEL_NAME, name);
			modelNode.put(ModelDataJsonConstants.MODEL_DESCRIPTION, description);
			modelNode.put(ModelDataJsonConstants.MODEL_REVISION, revision);

			model.setName(name);
			model.setKey(key);
			model.setMetaInfo(modelNode.toString());

			repositoryService.saveModel(model);
			String id = model.getId();

			// 完善ModelEditorSource
			ObjectNode editorNode = objectMapper.createObjectNode();
			editorNode.put("id", "canvas");
			editorNode.put("resourceId", "canvas");
			ObjectNode stencilSetNode = objectMapper.createObjectNode();
			stencilSetNode.put("namespace", "http://b3mn.org/stencilset/bpmn2.0#");
			editorNode.put("stencilset", stencilSetNode);
			repositoryService.addModelEditorSource(id, editorNode.toString().getBytes("utf-8"));

			response.sendRedirect(request.getContextPath() + "/activiti-explorer/modeler.html?modelId=" + id);
		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.info("模型创建失败！");
		}

	}

}