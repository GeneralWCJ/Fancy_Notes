/*
 * Copyright (c) 2022 Joseph Wilson
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.example.fancynotes

import com.example.fancynotes.model.Note
import com.example.fancynotes.model.getTruncatedBody
import org.junit.Assert.assertTrue
import org.junit.Test

/**
 * Tests that the Trim functionality in a [Note] works, and preforms as it should
 */
class TrimTesting {
    val TAG = "ExampleUnitTest"

    @Test
    fun trimStandard() {
        val toTrim: String = "You will rejoice to hear that no disaster has accompanied the " +
                "commencement of an enterprise which you have regarded with such evil forebodings. " +
                "I arrived here yesterday, and my first task is to assure my dear sister of my " +
                "welfare and increasing confidence in the success of my undertaking.\n" +
                "\n" +
                "I am already far north of London, and as I walk in the streets of Petersburgh, " +
                "I feel a cold northern breeze play upon my cheeks, which braces my nerves and " +
                "fills me with delight. Do you understand this feeling? This breeze, which has " +
                "travelled from the regions towards which I am advancing, gives me a foretaste " +
                "of those icy climes. Inspirited by this wind of promise, my daydreams become " +
                "more fervent and vivid. I try in vain to be persuaded that the pole is the " +
                "seat of frost and desolation; it ever presents itself to my imagination as " +
                "the region of beauty and delight. There, Margaret, the sun is for ever " +
                "visible, its broad disk just skirting the horizon and diffusing a perpetual " +
                "splendour. There—for with your leave, my sister, I will put some trust in " +
                "preceding navigators—there snow and frost are banished; and, sailing over a " +
                "calm sea, we may be wafted to a land surpassing in wonders and in beauty every " +
                "region hitherto discovered on the habitable globe. Its productions and features " +
                "may be without example, as the phenomena of the heavenly bodies undoubtedly are " +
                "in those undiscovered solitudes. What may not be expected in a country of eternal " +
                "light? I may there discover the wondrous power which attracts the needle and may " +
                "regulate a thousand celestial observations that require only this voyage to render " +
                "their seeming eccentricities consistent for ever. I shall satiate my ardent " +
                "curiosity with the sight of a part of the world never before visited, and may " +
                "tread a land never before imprinted by the foot of man. These are my " +
                "enticements, and they are sufficient to conquer all fear of danger or " +
                "death and to induce me to commence this laborious voyage with the joy a " +
                "child feels when he embarks in a little boat, with his holiday mates, " +
                "on an expedition of discovery up his native river. But supposing all these " +
                "conjectures to be false, you cannot contest the inestimable benefit which I " +
                "shall confer on all mankind, to the last generation, by discovering a passage " +
                "near the pole to those countries, to reach which at present so many months are " +
                "requisite; or by ascertaining the secret of the magnet, which, if at all " +
                "possible, can only be effected by an undertaking such as mine.\n"
        val note = Note(0, "test_title", toTrim, 0)
        val trimmed = note.getTruncatedBody()
        val expectedTrim = "You will rejoice to hear that no disaster has accompanied the " +
                "commencement of an enterprise which you have regarded with such evil forebodings. " +
                "I arrived here yesterday, and my first task is to assure my dear sister of my " +
                "welfare and increasing confidence in the success of my undertaking.\n" +
                "\n" +
                "I am already far north of London, and as I walk in the streets of Petersburgh, " +
                "I feel a cold northern breeze play upon my cheeks, which braces my nerves and " +
                "fills me with delight. Do you understand this feeling ..."
        assertTrue("Expected and actual were different", trimmed == expectedTrim)

    }

    @Test
    fun trimShort() {
        val toTrim: String = "You will rejoice to hear that no disaster has accompanied the " +
                "commencement of an enterprise which you have regarded with such evil forebodings. " +
                "I arrived here yesterday, and my first task is to assure my dear sister of my " +
                "welfare and increasing confidence in the success of my undertaking."
        val note = Note(0, "test_title", toTrim, 0)
        val trimmed = note.getTruncatedBody()
        assertTrue("Expected and actual were different", trimmed == toTrim)
    }

    @Test
    fun trimNone() {
        val toTrim = ""
        val note = Note(0, "test_title", toTrim, 0)
        val trimmed = note.getTruncatedBody()
        val expectedTrim = ""
        assertTrue("Expected and actual were different", trimmed == expectedTrim)
    }

    @Test
    fun trimNoSpacePeriod() {
        val toTrim: String =
            "Youwillrejoicetohearthatnodisasterhasaccompaniedthecommencementofanenter" +
                    "prisewhichyouhaveregardedwithsuchevilforebodings.Iarrivedhereyesterday,andmyfirstt" +
                    "askistoassuremydearsisterofmywelfareandincreasingconfidenceinthesuccessofmyundertaking.\n" +
                    "\n" +
                    "IamalreadyfarnorthofLondon,andasIwalkinthestreetsofPetersburgh,Ifeelacoldnorthernb" +
                    "reezeplayuponmycheeks,whichbracesmynervesandfillsmewithdelight.Doyouunderstandthis" +
                    "feeling?Thisbreeze,whichhastravelledfromtheregionstowardswhichIamadvancing,givesmea" +
                    "foretasteofthoseicyclimes.Inspiritedbythiswindofpromise,mydaydreamsbecomemoreferven" +
                    "tandvivid.Itryinvaintobepersuadedthatthepoleistheseatoffrostanddesolation;iteverpr" +
                    "esentsitselftomyimaginationastheregionofbeautyanddelight.There,Margaret,thesunisfo" +
                    "revervisible,itsbroaddiskjustskirtingthehorizonanddiffusingaperpetualsplendour.The" +
                    "re—forwithyourleave,mysister,Iwillputsometrustinprecedingnavigators—theresnowandfr" +
                    "ostarebanished;and,sailingoveracalmsea,wemaybewaftedtoalandsurpassinginwondersandi" +
                    "nbeautyeveryregionhithertodiscoveredonthehabitableglobe.Itsproductionsandfeaturesm" +
                    "aybewithoutexample,asthephenomenaoftheheavenlybodiesundoubtedlyareinthoseundiscove" +
                    "redsolitudes.Whatmaynotbeexpectedinacountryofeternallight?Imaytherediscoverthewond" +
                    "rouspowerwhichattractstheneedleandmayregulateathousandcelestialobservationsthatreq" +
                    "uireonlythisvoyagetorendertheirseemingeccentricitiesconsistentforever.Ishallsatiat" +
                    "emyardentcuriositywiththesightofapartoftheworldneverbeforevisited," +
                    ".Thesearemyenticements,andtheyaresufficienttoconquerallfearofdangerordeathandtoind" +
                    "ucemetocommencethislaboriousvoyagewiththejoyachildfeelswhenheembarksinalittleboat," +
                    "withhisholidaymates,onanexpeditionofdiscoveryuphisnativeriver.Butsupposingallthese" +
                    "conjecturestobefalse,youcannotcontesttheinestimablebenefitwhichIshallconferonallma" +
                    "nkind,tothelastgeneration,bydiscoveringapassagenearthepoletothosecountries,toreachw" +
                    "hichatpresentsomanymonthsarerequisite;orbyascertainingthesecretofthemagnet,which,if" +
                    "atallpossible,canonlybeeffectedbyanundertakingsuchasmine.\n"
        val note = Note(0, "test_title", toTrim, 0)
        val trimmed = note.getTruncatedBody()
        val expectedTrim =
            "Youwillrejoicetohearthatnodisasterhasaccompaniedthecommencementofanenter" +
                    "prisewhichyouhaveregardedwithsuchevilforebodings.Iarrivedhereyesterday,andmyfirstt" +
                    "askistoassuremydearsisterofmywelfareandincreasingconfidenceinthesuccessofmyundertaking.\n" +
                    "\n" +
                    "IamalreadyfarnorthofLondon,andasIwalkinthestreetsofPetersburgh,Ifeelacoldnorthernb" +
                    "reezeplayuponmycheeks,whichbracesmynervesandfillsmewithdelight.Doyouunderstandthis" +
                    "feeling?Thisbreeze,whichhastravelledfromtheregionstowardswhichIamadvancing,givesmea" +
                    "foretasteofthoseicyclimes ..."
        assertTrue("Expected and actual were different", trimmed == expectedTrim)
    }

    @Test
    fun trimNoSpaceBackslash() {
        val toTrim: String =
            "Youwillrejoicetohearthatnodisasterhasaccompaniedthecommencementofanenter" +
                    "prisewhichyouhaveregardedwithsuchevilforebodings.Iarrivedhereyesterday,andmyfirstt" +
                    "askistoassuremydearsisterofmywelfareandincreasingconfidenceinthesuccessofmyundertaking.\n" +
                    "\n" +
                    "IamalreadyfarnorthofLondon,andasIwalkinthestreetsofPetersburgh,Ifeelacoldnorthernb" +
                    "reezeplayuponmycheeks,whichbracesmynervesandfillsmewithdelight.Doyouunderstandthis" +
                    "feeling?Thisbreeze,whichhastravelledfromtheregionstowardswhichIamadvancing,givesmea" +
                    "foretasteofthoseicyclimes\\Inspiritedbythiswindofpromise,mydaydreamsbecomemoreferven" +
                    "tandvivid.Itryinvaintobepersuadedthatthepoleistheseatoffrostanddesolation;iteverpr" +
                    "esentsitselftomyimaginationastheregionofbeautyanddelight.There,Margaret,thesunisfo" +
                    "revervisible,itsbroaddiskjustskirtingthehorizonanddiffusingaperpetualsplendour.The" +
                    "re—forwithyourleave,mysister,Iwillputsometrustinprecedingnavigators—theresnowandfr" +
                    "ostarebanished;and,sailingoveracalmsea,wemaybewaftedtoalandsurpassinginwondersandi" +
                    "nbeautyeveryregionhithertodiscoveredonthehabitableglobe.Itsproductionsandfeaturesm" +
                    "aybewithoutexample,asthephenomenaoftheheavenlybodiesundoubtedlyareinthoseundiscove" +
                    "redsolitudes.Whatmaynotbeexpectedinacountryofeternallight?Imaytherediscoverthewond" +
                    "rouspowerwhichattractstheneedleandmayregulateathousandcelestialobservationsthatreq" +
                    "uireonlythisvoyagetorendertheirseemingeccentricitiesconsistentforever.Ishallsatiat" +
                    "emyardentcuriositywiththesightofapartoftheworldneverbeforevisited," +
                    ".Thesearemyenticements,andtheyaresufficienttoconquerallfearofdangerordeathandtoind" +
                    "ucemetocommencethislaboriousvoyagewiththejoyachildfeelswhenheembarksinalittleboat," +
                    "withhisholidaymates,onanexpeditionofdiscoveryuphisnativeriver.Butsupposingallthese" +
                    "conjecturestobefalse,youcannotcontesttheinestimablebenefitwhichIshallconferonallma" +
                    "nkind,tothelastgeneration,bydiscoveringapassagenearthepoletothosecountries,toreachw" +
                    "hichatpresentsomanymonthsarerequisite;orbyascertainingthesecretofthemagnet,which,if" +
                    "atallpossible,canonlybeeffectedbyanundertakingsuchasmine.\n"
        val note = Note(0, "test_title", toTrim, 0)
        val trimmed = note.getTruncatedBody()
        val expectedTrim =
            "Youwillrejoicetohearthatnodisasterhasaccompaniedthecommencementofanenter" +
                    "prisewhichyouhaveregardedwithsuchevilforebodings.Iarrivedhereyesterday,andmyfirstt" +
                    "askistoassuremydearsisterofmywelfareandincreasingconfidenceinthesuccessofmyundertaking.\n" +
                    "\n" +
                    "IamalreadyfarnorthofLondon,andasIwalkinthestreetsofPetersburgh,Ifeelacoldnorthernb" +
                    "reezeplayuponmycheeks,whichbracesmynervesandfillsmewithdelight.Doyouunderstandthis" +
                    "feeling?Thisbreeze,whichhastravelledfromtheregionstowardswhichIamadvancing,givesmea" +
                    "foretasteofthoseicyclimes ..."
        assertTrue("Expected and actual were different", trimmed == expectedTrim)
    }

    @Test
    fun trimNoSpaceQuestionMark() {
        val toTrim: String =
            "Youwillrejoicetohearthatnodisasterhasaccompaniedthecommencementofanenter" +
                    "prisewhichyouhaveregardedwithsuchevilforebodings.Iarrivedhereyesterday,andmyfirstt" +
                    "askistoassuremydearsisterofmywelfareandincreasingconfidenceinthesuccessofmyundertaking.\n" +
                    "\n" +
                    "IamalreadyfarnorthofLondon,andasIwalkinthestreetsofPetersburgh,Ifeelacoldnorthernb" +
                    "reezeplayuponmycheeks,whichbracesmynervesandfillsmewithdelight.Doyouunderstandthis" +
                    "feeling?Thisbreeze,whichhastravelledfromtheregionstowardswhichIamadvancing,givesmea" +
                    "foretasteofthoseicyclimes?Inspiritedbythiswindofpromise,mydaydreamsbecomemoreferven" +
                    "tandvivid.Itryinvaintobepersuadedthatthepoleistheseatoffrostanddesolation;iteverpr" +
                    "esentsitselftomyimaginationastheregionofbeautyanddelight.There,Margaret,thesunisfo" +
                    "revervisible,itsbroaddiskjustskirtingthehorizonanddiffusingaperpetualsplendour.The" +
                    "re—forwithyourleave,mysister,Iwillputsometrustinprecedingnavigators—theresnowandfr" +
                    "ostarebanished;and,sailingoveracalmsea,wemaybewaftedtoalandsurpassinginwondersandi" +
                    "nbeautyeveryregionhithertodiscoveredonthehabitableglobe.Itsproductionsandfeaturesm" +
                    "aybewithoutexample,asthephenomenaoftheheavenlybodiesundoubtedlyareinthoseundiscove" +
                    "redsolitudes.Whatmaynotbeexpectedinacountryofeternallight?Imaytherediscoverthewond" +
                    "rouspowerwhichattractstheneedleandmayregulateathousandcelestialobservationsthatreq" +
                    "uireonlythisvoyagetorendertheirseemingeccentricitiesconsistentforever.Ishallsatiat" +
                    "emyardentcuriositywiththesightofapartoftheworldneverbeforevisited," +
                    ".Thesearemyenticements,andtheyaresufficienttoconquerallfearofdangerordeathandtoind" +
                    "ucemetocommencethislaboriousvoyagewiththejoyachildfeelswhenheembarksinalittleboat," +
                    "withhisholidaymates,onanexpeditionofdiscoveryuphisnativeriver.Butsupposingallthese" +
                    "conjecturestobefalse,youcannotcontesttheinestimablebenefitwhichIshallconferonallma" +
                    "nkind,tothelastgeneration,bydiscoveringapassagenearthepoletothosecountries,toreachw" +
                    "hichatpresentsomanymonthsarerequisite;orbyascertainingthesecretofthemagnet,which,if" +
                    "atallpossible,canonlybeeffectedbyanundertakingsuchasmine.\n"
        val note = Note(0, "test_title", toTrim, 0)
        val trimmed = note.getTruncatedBody()
        val expectedTrim =
            "Youwillrejoicetohearthatnodisasterhasaccompaniedthecommencementofanenter" +
                    "prisewhichyouhaveregardedwithsuchevilforebodings.Iarrivedhereyesterday,andmyfirstt" +
                    "askistoassuremydearsisterofmywelfareandincreasingconfidenceinthesuccessofmyundertaking.\n" +
                    "\n" +
                    "IamalreadyfarnorthofLondon,andasIwalkinthestreetsofPetersburgh,Ifeelacoldnorthernb" +
                    "reezeplayuponmycheeks,whichbracesmynervesandfillsmewithdelight.Doyouunderstandthis" +
                    "feeling?Thisbreeze,whichhastravelledfromtheregionstowardswhichIamadvancing,givesmea" +
                    "foretasteofthoseicyclimes ..."
        assertTrue("Expected and actual were different", trimmed == expectedTrim)
    }

    @Test
    fun trimNoSpaceComma() {
        val toTrim: String =
            "Youwillrejoicetohearthatnodisasterhasaccompaniedthecommencementofanenter" +
                    "prisewhichyouhaveregardedwithsuchevilforebodings.Iarrivedhereyesterday,andmyfirstt" +
                    "askistoassuremydearsisterofmywelfareandincreasingconfidenceinthesuccessofmyundertaking.\n" +
                    "\n" +
                    "IamalreadyfarnorthofLondon,andasIwalkinthestreetsofPetersburgh,Ifeelacoldnorthernb" +
                    "reezeplayuponmycheeks,whichbracesmynervesandfillsmewithdelight.Doyouunderstandthis" +
                    "feeling?Thisbreeze,whichhastravelledfromtheregionstowardswhichIamadvancing,givesmea" +
                    "foretasteofthoseicyclimes,Inspiritedbythiswindofpromise,mydaydreamsbecomemoreferven" +
                    "tandvivid.Itryinvaintobepersuadedthatthepoleistheseatoffrostanddesolation;iteverpr" +
                    "esentsitselftomyimaginationastheregionofbeautyanddelight.There,Margaret,thesunisfo" +
                    "revervisible,itsbroaddiskjustskirtingthehorizonanddiffusingaperpetualsplendour.The" +
                    "re—forwithyourleave,mysister,Iwillputsometrustinprecedingnavigators—theresnowandfr" +
                    "ostarebanished;and,sailingoveracalmsea,wemaybewaftedtoalandsurpassinginwondersandi" +
                    "nbeautyeveryregionhithertodiscoveredonthehabitableglobe.Itsproductionsandfeaturesm" +
                    "aybewithoutexample,asthephenomenaoftheheavenlybodiesundoubtedlyareinthoseundiscove" +
                    "redsolitudes.Whatmaynotbeexpectedinacountryofeternallight?Imaytherediscoverthewond" +
                    "rouspowerwhichattractstheneedleandmayregulateathousandcelestialobservationsthatreq" +
                    "uireonlythisvoyagetorendertheirseemingeccentricitiesconsistentforever.Ishallsatiat" +
                    "emyardentcuriositywiththesightofapartoftheworldneverbeforevisited," +
                    ".Thesearemyenticements,andtheyaresufficienttoconquerallfearofdangerordeathandtoind" +
                    "ucemetocommencethislaboriousvoyagewiththejoyachildfeelswhenheembarksinalittleboat," +
                    "withhisholidaymates,onanexpeditionofdiscoveryuphisnativeriver.Butsupposingallthese" +
                    "conjecturestobefalse,youcannotcontesttheinestimablebenefitwhichIshallconferonallma" +
                    "nkind,tothelastgeneration,bydiscoveringapassagenearthepoletothosecountries,toreachw" +
                    "hichatpresentsomanymonthsarerequisite;orbyascertainingthesecretofthemagnet,which,if" +
                    "atallpossible,canonlybeeffectedbyanundertakingsuchasmine.\n"
        val note = Note(0, "test_title", toTrim, 0)
        val trimmed = note.getTruncatedBody()
        val expectedTrim =
            "Youwillrejoicetohearthatnodisasterhasaccompaniedthecommencementofanenter" +
                    "prisewhichyouhaveregardedwithsuchevilforebodings.Iarrivedhereyesterday,andmyfirstt" +
                    "askistoassuremydearsisterofmywelfareandincreasingconfidenceinthesuccessofmyundertaking.\n" +
                    "\n" +
                    "IamalreadyfarnorthofLondon,andasIwalkinthestreetsofPetersburgh,Ifeelacoldnorthernb" +
                    "reezeplayuponmycheeks,whichbracesmynervesandfillsmewithdelight.Doyouunderstandthis" +
                    "feeling?Thisbreeze,whichhastravelledfromtheregionstowardswhichIamadvancing,givesmea" +
                    "foretasteofthoseicyclimes ..."
        assertTrue("Expected and actual were different", trimmed == expectedTrim)
    }
}