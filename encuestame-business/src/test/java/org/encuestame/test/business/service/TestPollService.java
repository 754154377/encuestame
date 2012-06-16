/*
 ***********************************************************************************
 * Copyright (C) 2001-2011 encuestame: system online surveys Copyright (C) 2011
 * encuestame Development Team.
 * Licensed under the Apache Software License version 2.0
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to  in writing,  software  distributed
 * under the License is distributed  on  an  "AS IS"  BASIS,  WITHOUT  WARRANTIES  OR
 * CONDITIONS OF ANY KIND, either  express  or  implied.  See  the  License  for  the
 * specific language governing permissions and limitations under the License.
 ************************************************************************************
*/
package org.encuestame.test.business.service;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
 
import org.encuestame.business.service.PollService;
import org.encuestame.core.service.imp.IPollService;
import org.encuestame.core.util.ConvertDomainBean; 
import org.encuestame.persistence.domain.Email;
import org.encuestame.persistence.domain.EmailList;
import org.encuestame.persistence.domain.HashTag;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.security.Account;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.survey.Poll;
import org.encuestame.persistence.domain.survey.PollFolder;
import org.encuestame.persistence.exception.EnMeExpcetion;
import org.encuestame.persistence.exception.EnMeNoResultsFoundException;
import org.encuestame.test.business.security.AbstractSpringSecurityContext;
import org.encuestame.utils.enums.SearchPeriods;
import org.encuestame.utils.enums.TypeSearchResult;
import org.encuestame.utils.json.FolderBean;
import org.encuestame.utils.json.QuestionBean;
import org.encuestame.utils.web.HashTagBean;
import org.encuestame.utils.web.PollBean;
import org.encuestame.utils.web.UnitLists;
import org.hibernate.HibernateException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

 /**
 * Test for {@link PollService}.
 * @author Morales, Diana Paola paolaATencuestame.org
 * @since 17/05/2010 19:35:36
 * @version $Id:$
 */
public class TestPollService extends AbstractSpringSecurityContext{

     /** {@link Account} **/
    private Account user;

    /** {@link Question} **/
    private Question question;

    /** {@link IPollService} **/
    @Autowired
    private IPollService pollService;

    /** {@link Poll} **/
    private Poll poll;

    private UserAccount userAccount;

    /** {@link EmailList} **/
    private EmailList emailList;

    /** {@link Email} **/
    private Email emails;

    /** {@link PollFolder}. **/
    private PollFolder folder;

    /** **/
    private Integer MAX_RESULTS = 10;

    /** **/
    private Integer START = 0;
    
    /** **/
    private String[] answers = new String[4];
    
    /** **/
    private HashTag tag1;
    
    /** **/
	private HashTag tag2;
    
    /** **/
	private HashTag tag3;
    
    /** **/
	private HashTag tag4;
    
    /** {@link HashTagBean} list. **/
    private List<HashTagBean> tagBeanList = new ArrayList<HashTagBean>();

    /**
     * Init.
     */
    @Before
    public void serviceInit(){
        this.user = createUser("testEncuesta", "testEncuesta123");
        this.userAccount = createUserAccount("diana", this.user);
        this.question = createQuestion("Why the roses are red?","html");

        final Calendar calendarDate = Calendar.getInstance();
        calendarDate.add(Calendar.DAY_OF_WEEK,-1);
        final Date yesterdayDate= calendarDate.getTime(); 

        this.poll = createPoll(yesterdayDate, this.question, "FDK125", getSpringSecurityLoggedUserAccount(), Boolean.TRUE, Boolean.TRUE);
        this.emailList = createDefaultListEmail(this.userAccount.getAccount());
        createDefaultListEmail(this.user, "default");
        this.emails = createDefaultEmails("paola@demo.com", this.emailList);
        createDefaultEmails("dianmorales@demo.com", this.emailList);
        this.folder = createPollFolder("folder 1", getSpringSecurityLoggedUserAccount());
        this.poll.setPollFolder(folder); 
        
        // Answers.
        this.answers[0] = "answer One";
        this.answers[1] = "answer Two";
        this.answers[2] = "answer Three";
        this.answers[3] = "answer Four"; 
        
        // HashTags
		this.tag1 = createHashTag("one"); 
		this.tag2 = createHashTag("two");
		this.tag3 = createHashTag("three");
		this.tag4 = createHashTag("four");
		
		// HashtagBean List
		tagBeanList.add(ConvertDomainBean
				.convertHashTagDomain(this.tag1));
		tagBeanList.add(ConvertDomainBean
				.convertHashTagDomain(this.tag2));
		tagBeanList.add(ConvertDomainBean
				.convertHashTagDomain(this.tag3));
		tagBeanList.add(ConvertDomainBean
				.convertHashTagDomain(this.tag4));  
     }

    /**
     * Test createPoll.
     * @throws Exception exception
     */
    @Test
    public void testcreatePoll() throws Exception{
        final QuestionBean question = ConvertDomainBean.convertQuestionsToBean(this.question);
        final PollBean unitPoll = ConvertDomainBean.convertPollDomainToBean(this.poll);
        unitPoll.setQuestionBean(question);  
		
        final Poll myPoll = this.pollService.createPoll("ssss", this.answers, Boolean.TRUE, "APPROVE" ,Boolean.TRUE, this.tagBeanList);  
        Assert.assertNotNull(myPoll);
    }
    
    

    /**
     * Test getPollsByFolder.
     * @throws EnMeNoResultsFoundException
     */
    @Test
    //TODO: ignore for now.
    public void testgetPollsByFolder() throws EnMeNoResultsFoundException{
        getPollDao().saveOrUpdate(this.poll);
        List<PollBean> polls = this.pollService.getPollsByFolder(ConvertDomainBean
                              .convertFolderToBeanFolder(folder));
        assertEquals(1, polls.size());
    }

    /**
     * Test retrieveFolderPoll.
     * @throws EnMeNoResultsFoundException exception
     */
    @Test
    public void testretrieveFolderPoll() throws EnMeNoResultsFoundException{
        List<FolderBean> folders = this.pollService.retrieveFolderPoll();
        assertEquals(folders.size(), 1);
    }

    /**
     * Test createPollFolder.
     * @throws EnMeNoResultsFoundException exception
     */
    @Test
    public void testcreatePollFolder() throws EnMeNoResultsFoundException{
       this.pollService.createPollFolder("folder 2");
       List<FolderBean> folders = this.pollService.retrieveFolderPoll();
       assertEquals(folders.size(), 2);
    }

    /**
     * Test updateFolderName.
     * @throws EnMeNoResultsFoundException exception
     */
    @Test
    public void testupdateFolderName() throws EnMeNoResultsFoundException{
        this.pollService.updateFolderName(this.folder.getId(), "newFolderName", this.userAccount.getUsername());
        final PollFolder folder = this.getPollDao().getPollFolderById(this.folder.getId());
        assertEquals(folder.getFolderName(), "newFolderName");
    }

    /**
     * test removePollFolder.
     * @throws EnMeNoResultsFoundException exception
     */
    @Test(expected = HibernateException.class)
    public void testremovePollFolderBatchUpdateException() throws EnMeNoResultsFoundException{
        final long id = this.folder.getId();
        this.pollService.removePollFolder(id);
    }

    /**
     * Remove Poll Folder.
     * @throws EnMeNoResultsFoundException exception
     */
    @Test
    public void testremovePollFolder() throws EnMeNoResultsFoundException{
        this.poll.setPollFolder(null);
        getPollDao().saveOrUpdate(this.poll);
        final long id = this.folder.getId();
        this.pollService.removePollFolder(id);
        Assert.assertNull(getPollDao().getPollFolderById(id));
    }

    /**
     * Test Find Polls By User.
     * @throws EnMeNoResultsFoundException
     **/
    @Test
    public void testFindAllPollByUserId() throws EnMeNoResultsFoundException{
        List<PollBean> unitPoll =  new ArrayList<PollBean>();
        unitPoll = pollService.listPollByUser(5, 0);
        assertEquals("should be equals",1, unitPoll.size());
    }

    /**
     * Test Update Question Poll.
     * @throws EnMeExpcetion
     */
    @Test
    public void testUpdateQuestionPoll() throws EnMeExpcetion{
        final Question newQuestion = createQuestion("Why the tooth are white", "pattern");
        final PollBean pb = pollService.updateQuestionPoll(this.poll.getPollId(), newQuestion);
        assertEquals(newQuestion.getQuestion(), pb.getQuestionBean().getQuestionName());
     }

    /**
     * Test publish poll by email list.
     */
    @Test(timeout=80000)
    public void testPublicPollByEmailList(){
        final UnitLists emailUnitList = createUnitEmailList(this.emailList.getIdList(),
                        new Date(), this.emailList.getListName(), this.userAccount.getUid());
         pollService.publicPollByList(this.poll, emailUnitList);
         assertEquals(1, 1);
    }

    /**
     * Retrieve polls by specific date.
     * @throws EnMeNoResultsFoundException
     */
    @Test
    public void testGetPollsbyDate() throws EnMeNoResultsFoundException{
        final Calendar calendarDate = Calendar.getInstance();
        calendarDate.add(Calendar.DAY_OF_WEEK,-1);
        final Date yesterdayDate= calendarDate.getTime();
        final List<PollBean> pbean = this.pollService.getPollsbyDate(yesterdayDate, this.MAX_RESULTS, this.START);
        for (PollBean pollBean : pbean) {
             log.debug(" poll name and Id--> " + pollBean.getQuestionBean().getQuestionName() + "ID -->" + pollBean.getId());
        }
    }
    
    
    /**
     * Test remove poll
     * @throws EnMeExpcetion
     */
	@Test
	public void testRemovePoll() throws EnMeExpcetion {
		//this.answers[3] = "answer Four";

		final Poll newPollService = this.pollService.createPoll(
				"First test poll", this.answers, Boolean.TRUE, "APPROVE",
				Boolean.TRUE, this.tagBeanList);

		final List<QuestionAnswer> beforeAnswers = getQuestionDaoImp()
				.getAnswersByQuestionId(newPollService.getQuestion().getQid());
		assertEquals(beforeAnswers.size(), 4);

		this.pollService.removePoll(newPollService.getPollId());

		/*
		 * final Poll checkPoll = this.pollService.getPollById(newPollService
		 * .getPollId());
		 * 
		 * assertEquals("Should be equals", "poll invalid with this id" +
		 * newPollService.getPollId(), checkPoll);
		 */

		final List<QuestionAnswer> afterAnswers = getQuestionDaoImp()
				.getAnswersByQuestionId(newPollService.getQuestion().getQid());
		assertEquals(afterAnswers.size(), 0);

	}
	
	/**
	 * Test Remove HashTags from Poll.
	 * @throws EnMeExpcetion
	 */
	@Test
	public void testRemoveHashTagsFromPoll() throws EnMeExpcetion {
		final QuestionBean question = ConvertDomainBean
				.convertQuestionsToBean(this.question);
		final PollBean unitPoll = ConvertDomainBean
				.convertPollDomainToBean(this.poll);
		unitPoll.setQuestionBean(question);

		final Poll myPoll = this.pollService.createPoll("dddd", this.answers,
				Boolean.TRUE, "APPROVE", Boolean.TRUE, this.tagBeanList);

		Assert.assertNotNull(myPoll);
		final Poll myPoll2 = this.pollService.createPoll("eeee", this.answers,
				Boolean.TRUE, "APPROVE", Boolean.TRUE, this.tagBeanList);
		Assert.assertNotNull(myPoll2);

		final List<Poll> retrievePollsbyTagBeforeRemove = getPollDao()
				.getPollByHashTagName(this.tag1.getHashTag(), this.START,
						this.MAX_RESULTS, TypeSearchResult.HASHTAG, SearchPeriods.ALLTIME);
		assertEquals(retrievePollsbyTagBeforeRemove.size(), 2);

		// Remove hashtag
		myPoll.getHashTags().remove(tag1);
		getPollDao().saveOrUpdate(myPoll);

		final List<Poll> retrievePollsbyTagAfterRemove = getPollDao()
				.getPollByHashTagName(this.tag1.getHashTag(), this.START,
						this.MAX_RESULTS, TypeSearchResult.HASHTAG, SearchPeriods.ALLTIME);
		assertEquals(retrievePollsbyTagAfterRemove.size(), 1);
	}
}
