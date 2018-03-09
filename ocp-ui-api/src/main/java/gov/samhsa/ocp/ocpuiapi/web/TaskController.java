package gov.samhsa.ocp.ocpuiapi.web;

import feign.FeignException;
import gov.samhsa.ocp.ocpuiapi.infrastructure.FisClient;
import gov.samhsa.ocp.ocpuiapi.service.dto.ReferenceDto;
import gov.samhsa.ocp.ocpuiapi.service.dto.TaskDto;
import gov.samhsa.ocp.ocpuiapi.util.ExceptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("ocp-fis")
@Slf4j
public class TaskController {
    @Autowired
    FisClient fisClient;

    @PostMapping("/tasks")
    @ResponseStatus(HttpStatus.CREATED)
    public void createTask(@Valid @RequestBody TaskDto taskDto) {
        log.info("About to create a task");
        try {
            fisClient.createTask(taskDto);
            log.info("Successfully created a task.");
        }
        catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToResourceCreate(fe, " that the activity definition was not created");
        }
    }

    @GetMapping("/tasks/search")
    public Object searchTasks(@RequestParam(value = "statusList", required = false) List<String> statusList,
                              @RequestParam(value = "searchType", required = false) String searchType,
                              @RequestParam(value = "searchValue", required = false) String searchValue,
                              @RequestParam(value = "pageNumber", required = false) Integer pageNumber,
                              @RequestParam(value = "pageSize", required = false) Integer pageSize) {
        log.info("Searching Tasks from FHIR server");
        try {
            Object tasks = fisClient.searchTasks(statusList, searchType, searchValue, pageNumber, pageSize);
            log.info("Got Response from FHIR server for Tasks Search");
            return tasks;
        } catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe, "No Tasks were found in configured FHIR server for the given searchType and searchValue");
            return null;
        }
    }

    @PutMapping("/tasks/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateTask(@PathVariable String taskId, @Valid @RequestBody TaskDto taskDto){
        try{
            fisClient.updateTask(taskId,taskDto);
            log.debug("Successfully updated a task");
        }catch(FeignException fe){
            ExceptionUtil.handleFeignExceptionRelatedToResourceUpdate(fe,"Task could not be updated in the FHIR server");
        }
    }

    @PutMapping("/tasks/{taskId}/deactivate")
    @ResponseStatus(HttpStatus.OK)
    public void deactivateTask(@PathVariable String taskId){
        try{
            fisClient.deactivateTask(taskId);
            log.debug("Successfully cancelled the task.");
        }catch (FeignException fe){
            ExceptionUtil.handleFeignExceptionRelatedToResourceInactivation(fe,"Task could not be deactivated in the FHIR server");
        }
    }

    @GetMapping("/tasks/{taskId}")
    public Object getTaskById(@PathVariable String taskId){
        try{
            return fisClient.getTaskById(taskId);
        }catch (FeignException fe){
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe,"Task could not be found");
            return null;
        }
    }

    @GetMapping("/tasks/task-references")
    public List<ReferenceDto> getRelatedTasks(String patient) {
        try {
            return fisClient.getRelatedTasks(patient);
        } catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe, "Task could not be found for the given patientId");
            return null;
        }
    }

    @GetMapping("/tasks")
    public List<TaskDto> getUpcomingTasks(@RequestParam(value = "practitioner") String practitioner) {
        try {
            return fisClient.getUpcomingTasks(practitioner);
        } catch (FeignException fe) {
            ExceptionUtil.handleFeignExceptionRelatedToSearch(fe, "Task could not be found for the given practitioner");
            return null;
        }
    }



}
