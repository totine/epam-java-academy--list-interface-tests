package test.java;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class ListTest {

    private List<Object> listToTest;
    private List<Integer> integerListToTest;
    private Class listImplementation;

    private Object sampleObject1;
    private Object sampleObject2;
    private Object sampleObject3;
    private Object[] objectArray;


    private ListTest(List<Object> listToTest) {
        listImplementation = listToTest.getClass();
    }

    public static ListTest linkedList() {
        return new ListTest(new LinkedList<>());
    }

    public static ListTest arrayList() {
        return new ListTest(new ArrayList<>());
    }

    public static void main(String[] args) {

        ListTest linkedListTests = ListTest.linkedList();
        ListTest arrayListListTests = ListTest.arrayList();

        linkedListTests.callAllTestMethods();
        arrayListListTests.callAllTestMethods();

    }

    private void setUpNewEmptyList() {

        try {
            listToTest = (List<Object>) listImplementation.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void setUpNewEmptyIntegerList() {

        try {
            integerListToTest = (List<Integer>) listImplementation.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void cleanAfterAssignNullToList() {
        setUpNewEmptyList();
    }

    private void createSampleObjects() {
        sampleObject1 = new Object();
        sampleObject2 = new Object();
        sampleObject3 = new Object();
    }

    private void createArrayOfSampleObject(int numberOfObject) {
        objectArray = new Object[numberOfObject];
        for (int i = 0; i < numberOfObject; i++) {
            objectArray[i] = new Object();
        }
    }

    private void setUpListWithThreeObjectElements() {
        setUpNewEmptyList();
        createSampleObjects();
        listToTest.add(sampleObject1);
        listToTest.add(sampleObject2);
        listToTest.add(sampleObject3);
    }

    private int getRandomInt(int min, int max) {
        int randomInt = (int) (Math.random() * (max - min) + min);
        return randomInt;
    }

    @Test(name = "size()")
    public void afterCreateNewList_listSizeEqualsZero() {
        // given
        setUpNewEmptyList();

        // when

        // then
        assert 0 == listToTest.size() : "incorrect list size";
    }

    @Test(name = "size()")
    public void afterAddThreeElementsToList_listSizeEqualsThree() {
        // given
        setUpNewEmptyList();
        createSampleObjects();

        // when
        listToTest.add(sampleObject1);
        listToTest.add(sampleObject1);
        listToTest.add(sampleObject1);

        //then
        assert 3 == listToTest.size() : "incorrect list size";
    }

    @Test(name = "add()")
    public void afterAddRandomNumberElementsToList_listSizeEqualsThisRandomNumber() {
        // given
        setUpNewEmptyList();
        int randomInt = getRandomInt(1, 100);
        createArrayOfSampleObject(randomInt);

        // when
        for (Object object : objectArray) {
            listToTest.add(object);
        }

        //then
        assert randomInt == listToTest.size() : "incorrect list size";
    }

    @Test(name = "add()")
    public void afterAddTheSameObjectToList_theListSizeIncearesByOne() {
        // given
        setUpNewEmptyList();
        createSampleObjects();
        listToTest.add(sampleObject1);
        listToTest.add(sampleObject2);
        listToTest.add(sampleObject3);

        // when
        listToTest.add(sampleObject3);

        // then
        int correctSize = 4;
        assert listToTest.size() == correctSize : "object not in list";
    }

    @Test(name = "addAll()")
    public void afterAddAllRandomNumberElementsToList_listSizeEqualsThisRandomNumber() {
        // given
        setUpNewEmptyList();
        int randomInt = getRandomInt(1, 100);
        createArrayOfSampleObject(randomInt);

        // when
        listToTest.addAll(Arrays.asList(objectArray));

        //then
        assert randomInt == listToTest.size() : "incorrect list size";
    }

    @Test(name = "isEmpty()")
    public void ifListIsNull_afterCallingIsEmpty_NullPointerExceptionIsThrown() {
        // given
        setUpNewEmptyList();
        listToTest = null;

        // when
        boolean isNPE = false;
        try {
            listToTest.isEmpty();
        }
        catch (NullPointerException e) {
            isNPE = true;
        }

        // then
        assert isNPE : "NullPointerException hasn't thrown";
        cleanAfterAssignNullToList();
    }

    @Test(name = "isEmpty()")
    public void afterCreateNewList_isEmptyIsTrue() {
        // given
        setUpNewEmptyList();

        // when

        // then
        assert listToTest.isEmpty() : "list should be empty";
    }

    @Test(name = "isEmpty()")
    public void afterAddObjectToList_isEmptyIsFalse() {
        // given
        setUpNewEmptyList();

        // when
        listToTest.add(new Object());

        // then
        assert !listToTest.isEmpty() : "list shouldn't be empty";
    }

    @Test(name = "add(), contains()")
    public void afterAddObjectToList_theListContainsTheObject() {
        // given
        setUpNewEmptyList();

        // when
        Object objectToAdd = new Object();
        listToTest.add(objectToAdd);

        // then
        assert listToTest.contains(objectToAdd) : "object not in list";
    }

    @Test(name = "contains()")
    public void objectThatWasntAddedToList_shouldntBeInEmptyList() {
        // given
        setUpNewEmptyList();
        Object objectToAdd = new Object();

        // when

        // then
        assert !listToTest.contains(objectToAdd) : "object shouldn't be in list";
    }

    @Test(name = "listIterator()")
    public void listIteratorOnNonEmptyList_ReturnsListIteratorObject() {
        // given
        setUpListWithThreeObjectElements();

        // when

        // then
        assert listToTest.listIterator() instanceof ListIterator : "";
    }

    @Test(name = "listIterator()")
    public void listIteratorOnEmptyList_ReturnsListIteratorObject() {
        // given
        setUpNewEmptyList();

        // when

        // then
        assert listToTest.listIterator() instanceof ListIterator : "";
    }

    @Test(name = "remove(int)")
    public void afterRemoveElementByIndex_listSizeDecreasedByOne() {
        // given
        setUpListWithThreeObjectElements();
        // when
        listToTest.remove(1);
        // then
        assert listToTest.size() == 2 : "";
    }

    @Test(name = "remove(int)")
    public void afterRemoveElementByIndex_listDoesntContainsThisElement() {
        // given
        setUpListWithThreeObjectElements();
        // when
        listToTest.remove(1);
        // then
        assert ! listToTest.contains(sampleObject2) : "";
    }


    public void callAllTestMethods() {
        Method[] methods = this.getClass().getDeclaredMethods();
        for (Method m : methods) {
            if (isTestMethod(m)) {
                try {
                    m.invoke(this);
                    System.out.format("OK %s %s passed\n", listToTest.getClass().getSimpleName(), m.getName());
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    System.out.format("BAD %s %s FAILED %s\n", listToTest.getClass().getSimpleName(), m.getName(), e.getCause().getMessage());
                }
            }
        }
    }

    private boolean isTestMethod(Method method) {
        boolean isTest = false;
        Annotation[] annotations = method.getDeclaredAnnotations();
        if (annotations.length == 0)
            return false;
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(Test.class)) {
                isTest = true;
                break;
            }
        }
        return isTest;
    }
}
