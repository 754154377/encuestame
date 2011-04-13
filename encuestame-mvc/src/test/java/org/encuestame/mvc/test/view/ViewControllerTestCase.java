/*
 ************************************************************************************
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
package org.encuestame.mvc.test.view;

import static org.springframework.test.web.ModelAndViewAssert.assertViewName;
import junit.framework.Assert;

import org.bouncycastle.util.test.TestFailedException;
import org.encuestame.mvc.controller.json.MethodJson;
import org.encuestame.mvc.test.config.AbstractMvcUnitBeans;
import org.encuestame.mvc.view.AdmonController;
import org.encuestame.mvc.view.DashBoardController;
import org.encuestame.mvc.view.HomeController;
import org.encuestame.mvc.view.LoginController;
import org.encuestame.mvc.view.PollController;
import org.encuestame.mvc.view.SurveyController;
import org.encuestame.mvc.view.TweetPollController;
import org.encuestame.persistence.domain.question.Question;
import org.encuestame.persistence.domain.question.QuestionAnswer;
import org.encuestame.persistence.domain.security.UserAccount;
import org.encuestame.persistence.domain.tweetpoll.TweetPoll;
import org.encuestame.persistence.domain.tweetpoll.TweetPollSwitch;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;


/**
 * Description.
 * @author Picado, Juan juanATencuestame.org
 * @since Apr 10, 2011
 */
public class ViewControllerTestCase extends AbstractMvcUnitBeans{

        @Autowired
        private TweetPollController pollController;

        @Before
        public void initMVc() {

        }

        /**
         * home view.
         * @throws Exception exception
         */
        @Test
        public void testHome() throws Exception {
            final HomeController controller = new HomeController();
            request = new MockHttpServletRequest(MethodJson.GET.toString(), "/home");
            final ModelAndView mav = handlerAdapter.handle(request, response,
                controller);
            assertViewName(mav, "redirect:/user/signin");
        }

        /**
         * Index view.
         * @throws Exception exception
         */
        @Test
        public void testIndexView() throws Exception {
            HomeController controller = new HomeController();
            request = new MockHttpServletRequest(MethodJson.GET.toString(), "/");
            final ModelAndView mav = handlerAdapter.handle(request, response,
                controller);
            assertViewName(mav, "redirect:/home");
        }

        /**
         * Dashboard view.
         * @throws Exception
         */
        @Test
        public void testDashBoardController() throws Exception {
            DashBoardController controller = new DashBoardController();
            request = new MockHttpServletRequest(MethodJson.GET.toString(), "/user/dashboard");
            final ModelAndView mav = handlerAdapter.handle(request, response,
                controller);
            assertViewName(mav, "dashboard");
        }

        /**
         * Test {@link AdmonController}.
         * @throws Exception
         */
        @Test
        public void testAdmonController() throws Exception {
            final AdmonController controller = new AdmonController();
            //location
            request = new MockHttpServletRequest(MethodJson.GET.toString(), "/admon/location");
            final ModelAndView mav = handlerAdapter.handle(request, response,
                controller);
            assertViewName(mav, "location");
            //members
            request = new MockHttpServletRequest(MethodJson.GET.toString(), "/admon/members");
            final ModelAndView mav2 = handlerAdapter.handle(request, response,
                controller);
            assertViewName(mav2, "members");
            //project
            //members
            request = new MockHttpServletRequest(MethodJson.GET.toString(), "/admon/project");
            final ModelAndView mav3 = handlerAdapter.handle(request, response,
                controller);
            assertViewName(mav3, "project");
        }

        /**
         * Test {@link LoginController}.
         * @throws Exception exception.
         */
        @Test
        public void testLoginController() throws Exception {
            final LoginController controller = new LoginController();
            //"/user/signin
            request = new MockHttpServletRequest(MethodJson.GET.toString(), "/user/signin");
            final ModelAndView mav = handlerAdapter.handle(request, response,
                controller);
            assertViewName(mav, "signin");
            // forgot view.
            request = new MockHttpServletRequest(MethodJson.GET.toString(), "/user/forgot");
                    final ModelAndView mav2 = handlerAdapter.handle(request, response,
                        controller);
                    assertViewName(mav2, "forgot");
        }

        /**
         * Test {@link LoginController}.
         * @throws Exception exception.
         */
        @Test
        public void testPollController() throws Exception {
            final PollController controller = new PollController();
            //"/user/signin
            request = new MockHttpServletRequest(MethodJson.GET.toString(), "/user/poll");
            final ModelAndView mav = handlerAdapter.handle(request, response,
                controller);
            assertViewName(mav, "poll");
        }

        /**
         * {@link TestFailedException} {@link SurveyController}.
         * @throws Exception exception.
         */
        @Test
        public void testSurveyController() throws Exception {
            final SurveyController controller = new SurveyController();
            //"/user/signin
            request = new MockHttpServletRequest(MethodJson.GET.toString(), "/user/survey");
            final ModelAndView mav = handlerAdapter.handle(request, response,
                controller);
            assertViewName(mav, "survey");
        }

        /**
         * Test {@link TweetPollController}.
         * @throws Exception exception.
         */
        @Test
        public void testTweetPollController() throws Exception {
            final UserAccount userAccount = createUserAccount("jota", createAccount());
            createFakesTweetPoll(userAccount);
            System.out.println(getHibernateTemplate().find("from TweetPoll").size());
            final TweetPoll tp1 = (TweetPoll) getHibernateTemplate().find("from TweetPoll").get(0);
            final Question q1 = tp1.getQuestion();
            final QuestionAnswer a1 = createQuestionAnswer("yes", q1, "12345");
            final QuestionAnswer a2 = createQuestionAnswer("no", q1, "12346");
            final TweetPollSwitch tps1 = createTweetPollSwitch(a1, tp1);
            final TweetPollSwitch tps2 = createTweetPollSwitch(a2, tp1);
            Assert.assertNotNull(pollController);

            //bad vote view.
            request = new MockHttpServletRequest(MethodJson.GET.toString(), "/tweetpoll/vote/1");
            final ModelAndView mav = handlerAdapter.handle(request, response,
                    pollController);
            System.out.println(mav);
            assertViewName(mav, "badTweetVote");

            //vote view.
            request = new MockHttpServletRequest(MethodJson.GET.toString(), "/tweetpoll/vote/"+tps1.getCodeTweet());
            final ModelAndView mavVote = handlerAdapter.handle(request, response,
                    pollController);
            System.out.println(mavVote);
            assertViewName(mavVote, "tweetVoted");

            //repeated vote view.
            request = new MockHttpServletRequest(MethodJson.GET.toString(), "/tweetpoll/vote/"+tps1.getCodeTweet());
            final ModelAndView mavVote1 = handlerAdapter.handle(request, response,
                    pollController);
            System.out.println(mavVote1);
            assertViewName(mavVote1, "repeatedTweetVote");


            ///user/tweetpoll/list
            request = new MockHttpServletRequest(MethodJson.GET.toString(), "/user/tweetpoll/list");
            final ModelAndView mav2 = handlerAdapter.handle(request, response,
                    pollController);
            System.out.println(mav2);
            assertViewName(mav2, "tweetpoll");

            ///user/tweetpoll/list
            request = new MockHttpServletRequest(MethodJson.GET.toString(), "/user/tweetpoll/new");
            final ModelAndView mav3 = handlerAdapter.handle(request, response,
                    pollController);
            System.out.println(mav3);
            assertViewName(mav3, "tweetpoll/new");

        }

        /**
         * @param pollController the pollController to set
         */
        public void setPollController(TweetPollController pollController) {
            this.pollController = pollController;
        }


}