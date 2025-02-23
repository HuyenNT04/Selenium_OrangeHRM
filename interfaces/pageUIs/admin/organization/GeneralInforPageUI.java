package pageUIs.admin.organization;

public class GeneralInforPageUI {
    //UserInterface: HTML/Xpath/Css=Locator
        public static final String ADMIN_SECTION = "//span[text()='Admin']";
        public static final String ORGANIZATION_ITEM = "//span[text()='Organization ']";
        public static final String GENERAL_INFORMATION_OPTION = "//a[text()='General Information']";
        public static final String GENERAL_INFORMATION_TITLE = "//h6[text()='General Information']";
        public static final String NUMBER_LABEL = "//label[text()='Number of Employees']";
        public static final String EDIT_TOGGLE = "//input[@type='checkbox']/following-sibling::span";
        public static final String ORGANIZATION_NAME_TEXTBOX = "//label[text()='Organization Name']/parent::div/following-sibling::div/input";
        public static final String PHONE_TEXTBOX = "//label[text()='Phone']/parent::div/following-sibling::div/input";
        public static final String EMAIL_TEXTBOX = "//label[text()='Email']/parent::div/following-sibling::div/input";
        public static final String COUNTRY_DROPDOWN = "//label[text()='Country']/parent::div/following-sibling::div/div[@class='oxd-select-wrapper']";
        public static final String COUNTRY_LISTVALUE = "//div[@role='option']";
        public static final String SAVE_BUTTON = "//button[@type='submit']";
        public static final String SUCCESS_MESSAGE = "//p[@class='oxd-text oxd-text--p oxd-text--toast-message oxd-toast-content-text']";
}