/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package plt.gui.help;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import plt.gui.component.ModalPopup;

/**
 *
 * @author luca
 */
public class Tab4Help extends ModalPopup {
        public void show(Parent parent, final EventHandler eventHandler) {
            
            WebView w = new WebView();
            w.setMaxSize(450 , 400);
           
            WebEngine e = w.getEngine();
            e.loadContent("<h1>Preference Learning Toolbox</h1>\n" +
"<h2>Preference Learning Algorithms</h2>\n" +
"Once data is loaded, features preprocessed and a feature selection strategy selected, this screen shows the configuration of the Preference Learning algorithm that creates the final model.\n" +
"<h3>Evaluation method</h3>\n" +
"The user can select the training algorithm and the method to evaluate its performance.\n" +
"<h4>Training algorithms</h4>\n" +
"Two training algorithms for <b>multi-layer perceptrons</b> are supported, namely <b>backpropagation</b> and <b>neuroevolution</b>.\n" +
"<ul>\n" +
"	<li>Multi-layer perceptron: this is the most common artificial neural network topology. It features a set of neurons organized in a sequence of layers. The output of each neuron is an input to every neuron in the next layer. The input features are connected to all the neurons in the first layer and the output of the neurons in the last layer form the outputs of the network. The user can choose the <b>number of layers</b>, the <b>number of neurons per layer</b> (except for the output layer which contains a single neuron) and the <b>activation function</b> of each neuron, affecting the complexity of the model trained.</li>\n" +
"	<li>Backpropagation: this is a gradient-descent algorithm that iteratively (over a given <b>number of epochs</b>) optimizes an error function by adjusts the weights of the network proportionally to the gradient of the error with respect to the current value of the weights and current data samples. The proportion and therefofore the strength of each update is regulated by the given <b>learning rate</b>. The error function used is the Rank Margin function which for a given pair of data samples (A and B, with A preferred over B) yields 0 if the network output for A  (f<sup>A</sub>) is more than one unit larger than the network output for B (f<sup>B</sub>) and 1.0-((f<sup>A</sub>)-(f<sup>B</sub>)) otherwise. The total error is averaged over the complete set of pairs in the training set. If the error is below a given <b>threshold</b>, training stops before reaching the specified number of epochs, and the current weight values are returned as final model.</li>\n" +
"	<li>Neuro-evolution: this is a genetic-search based algorithm that iteratively optimizes a set (population) of neural networks (individuals) to maximize a fitness function. The fitest neural network after a given number of iterations (<b>generations</b>) is returned as the trained model. The set of individuals on each generation is created by copying a given fraction of the fittest individuals in the previous generation (<b>elitism size</b>) and filling the rest of the population (<b>population size</b>) with offspring resulting from combining pairs of individuals in the previous population (parents). Parents are selected using a <b>roulette wheel</b> algorithm that selects two individuals proportionally to their fitness from a given fraction of the fittest individuals (<b>parents</b>). Once a pair of individuals is selected, they are combined applying a cross-over operator (<b>cross-over type</b>) with a given probability (<b>cross-over probability</b>) and a Gaussian mutation (mean equal to 0.0 and standard deviation equal to 0.1) to each gene (network weight) with a given probability (<b>mutation probability</b>). For each network and pair in the dataset, the fitness function calculates the logistic sigmoid function of the difference between the network's output for the preferred and the network's output for the non-perferred objects in the pair. The parameter of the logistic function (which multiplies the output difference) is equal to 30 if the difference is positive and 5.0 otherwise. The fitness of a network is calculated as the average of this value over the complete set of training pairs.</li>\n" +
"</ul>\n" +
"<h4>Evaluation method</h4>\n" +
"The user can choose to either train the models using the complete dataset (<b>no validation</b>) and therefore assesssing the performance as the percentage of correctly classified training pairs or can test the generability of the results by using <b>K-fold cross validation</b> that will train <b>k</b> models using different partitions of the data and return the percentage of correctly classified pairs not used for training (validation accuracy).\n" +
"\n" +
"<h2>Execution and Results</h2>\n" +
"With the algorithm configured, pressing the button <b>run</b> will start the feature selection and model training algorithms and show an approximated progress report. Once the whole process is completed, a summary screen with the configuration of each step as well as the training and validation accuracies of the final models are displayed. The final models can be saved individually by clicking on their accuracy pressing <b>Save Model</b>.");
            super.show(w, parent,null,new Button("close"), 500,500,false);  
        }
}
